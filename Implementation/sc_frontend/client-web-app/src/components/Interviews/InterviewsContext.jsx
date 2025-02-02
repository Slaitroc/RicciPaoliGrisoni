import React, {
  useEffect,
  useState,
  useContext,
  createContext,
  useRef,
} from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import { Alert } from "@mui/material";
import { useParams } from "react-router-dom";
import * as logger from "../../logger/logger";
import * as interview from "../../api-calls/api-wrappers/Interview/interview";
import { useNavigate } from "react-router-dom";

const InterviewsContext = createContext();

export const useInterviewsContext = () => {
  const context = useContext(InterviewsContext);
  if (!context) {
    throw new Error(
      "useInterviewsContext must be used within a InterviewsProvider"
    );
  }
  return context;
};

// if ((status, hasAnswered)) {
//       if (userType === STUDENT_USER_TYPE) {
//         if (status === "toBeSubmitted") return;
//         else if (status === "submitted" && hasAnswered === "false") return;
//         else if (status === "passed" || status === "failed") return;
//         else if (status === "submitted" && hasAnswered === "true") return;
//       } else if (userType === COMPANY_USER_TYPE) {
//         if (status === "toBeSubmitted") return;
//         else return;
//       }
//     } else return;
export const InterviewsProvider = ({ children }) => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [openAlert, setOpenAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertSeverity, setAlertSeverity] = useState();
  const openAlertProc = (message, severity) => {
    // setAlertSeverity(severity);
    // setAlertMessage(message);
    // setOpenAlert(true);
    logger.debug("fake alert", message, severity);
  };

  const [interviewObject, setInterviewObject] = useState({});
  const [interviewsArray, setInterviewsArray] = useState([]);
  const value = {
    openAlertProc,
    interviewsArray,
    setInterviewsArray,
    interviewObject,
    setInterviewObject,
  };

  return (
    <InterviewsContext.Provider value={value}>
      {openAlert && (
        <Alert onClose={() => setOpenAlert(false)} severity={alertSeverity}>
          {alertMessage}
        </Alert>
      )}
      {children}
    </InterviewsContext.Provider>
  );
};
