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
  const { profile } = useGlobalContext();
  const { id } = useParams();
  const newInterviewRef = useRef({});
  const interviewID = useRef(null);

  const [interviewsArray, setInterviewsArray] = useState([]);
  const [interviewDataSnapshot, setInterviewDataSnapshot] = useState(null);
  const [openAlert, setOpenAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertSeverity, setAlertSeverity] = useState("success");
  const [alertCloseTriggered, setAlertCloseTriggered] = useState(false);

  const [forceRender, setForceRender] = useState(0);

  // //DEBUG
  // useEffect(() => {
  //   logger.focus("INTERVIEW-ARRAY", interviewsArray);
  //   logger.focus("SNAPSHOT", interviewDataSnapshot);
  //   logger.focus("REF", id, newInterviewRef?.current);
  // }, [interviewsArray, interviewDataSnapshot, forceRender]);

  const closeAlertWithDelay = () => {
    //logger.debug("Closing alert with delay", alertCloseTriggered);
    // if (!alertCloseTriggered) {
    //   setAlertCloseTriggered(true);
    //   setTimeout(() => {
    //     setOpenAlert(false);
    //     setAlertMessage("");
    //     setAlertSeverity("success");
    //     setAlertCloseTriggered(false);
    //   }, 5000);
    // }
  };

  const openAlertProc = (message, severity) => {
    logger.debug("Opening alert with message: ", openAlert);
    setAlertSeverity(severity);
    setAlertMessage(message);
    setOpenAlert(true);
  };

  /**
   * Sends the interview questions to the backend that will create a template with the questions and associate it with the interview.
   * An interview with associated questions is automatically sent to the student, means has a state "Submitted"
   * @param {string} id
   * @param {string[]} data
   */
  const sendInterview = async (id, data) => {
    logger.focus("Creating interview with ID: ", interviewID.current);
    logger.focus("Creating interview with data: ", data);
    const response = await interview.sendInterviewQuestions(
      interviewID.current,
      data
    );
    if (response.success === true) {
      await fetchedData(); // Reload the interviewsArray
      reloadSnapshot(response.data.id); // Reload the snapshot of the created interview (Update the status: "Submitted")
      setAlertSeverity(response.severity);
      setAlertMessage(response.message);
      openAlertProc(); // Ensure alert is opened after setting message and severity
      closeAlertWithDelay();
      navigate(`/dashboard/interviews/details/${response.data.id}`);
    } else {
      setAlertSeverity(response.severity);
      setAlertMessage(response.message);
      openAlertProc(); // Ensure alert is opened after setting message and severity
    }
  };
  /**
   * Send the company evaluation to the student answers
   * @param {string} id
   * @param {int} evaluation
   */
  const evaluateInterview = async (id, evaluation) => {
    //TODO
    logger.log("Interview evaluation", evaluation);
  };

  /**
   * Reloads the snapshot of the interview from the {@link interviewsArray}
   *  */
  const reloadSnapshot = (OfferID) => {
    const snapshot = interviewsArray.reduce((acc, item) => {
      if (item.id.value == OfferID) return { ...acc, ...item };
      return acc;
    }, {});
    if (Object.keys(snapshot).length === 0) {
      openAlertProc("Offer not Found --> this alert may be buggy", "error");
    } else {
      closeAlertWithDelay();
      setInterviewDataSnapshot(snapshot);
    }
  };

  /**
   *
   * @returns {Promise<Response>} Fetches the interviews data from the backend and sets the {@link interviewsArray}
   */
  const fetchedData = async () => {
    return await interview.getFormattedInterviews().then((response) => {
      if (response.success === false) {
        logger.debug(response);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
        openAlertProc(); // Ensure alert is opened after setting message and severity
      } else {
        closeAlertWithDelay();
        setInterviewsArray(response.data);
      }
    });
  };

  useEffect(() => {
    fetchedData();
  }, []);

  //obtains the snapshot of the offer data from the id url parameter
  useEffect(() => {
    if (id === undefined || id === null || id === "") {
      return;
    }
    if (interviewsArray.length === 0) {
      return;
    }
    const snapshot = interviewsArray.reduce((acc, item) => {
      if (item.id.value == id) return { ...acc, ...item };
      return acc;
    }, {});
    if (Object.keys(snapshot).length === 0) {
      openAlertProc();
      setAlertSeverity("error");
      setAlertMessage("Offer not found");
    } else {
      closeAlertWithDelay();
      setInterviewDataSnapshot(snapshot);
      newInterviewRef.current = snapshot;
    }
  }, [id, interviewsArray]);

  const value = {
    interviewsArray,
    openAlert,
    interviewDataSnapshot,
    interviewID,
    newInterviewRef,
    reloadSnapshot,
    setInterviewDataSnapshot,
    setForceRender,
    sendInterview,
    openAlertProc,
    closeAlertWithDelay,
    evaluateInterview,
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
