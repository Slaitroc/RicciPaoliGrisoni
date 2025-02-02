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

export const InterviewsProvider = ({ children }) => {
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
  const navigate = useNavigate();

  //DEBUG
  useEffect(() => {
    logger.debug("InterviewsArray", interviewsArray);
    logger.debug("InterviewObject", interviewObject);
  }, [interviewsArray, interviewObject]);

  const interviewArrayFetch = async () => {
    const response = await interview.getFormattedInterviews();
    if (response.success === true) {
      logger.focus(response);
      setInterviewsArray(response.data);
    } else {
      openAlertProc("Failed to fetch interviews", "error");
    }
  };

  useEffect(() => {
    interviewArrayFetch();
  }, []);

  const value = {
    openAlertProc,
    interviewsArray,
    setInterviewsArray,
    interviewObject,
    setInterviewObject,

    interviewArrayFetch,
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
