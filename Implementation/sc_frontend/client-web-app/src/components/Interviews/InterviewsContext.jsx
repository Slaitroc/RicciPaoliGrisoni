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
  const interviewID = useRef(null);

  const [interviewsArray, setInterviewsArray] = useState([]);
  const [interviewDataSnapshot, setInterviewDataSnapshot] = useState(null);
  const [questions, setQuestions] = useState({});
  const [answers, setAnswers] = useState({
    id: "",
    answer1: "No answer",
    answer2: "No answer",
    answer3: "No answer",
    answer4: "No answer",
    answer5: "No answer",
    answer6: "No answer",
    evaluation: "",
  });
  const [openAlert, setOpenAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertSeverity, setAlertSeverity] = useState("success");
  const [alertCloseTriggered, setAlertCloseTriggered] = useState(false);

  const [arrayRefetch, setArrayRefetch] = useState(0);
  const arrayRefetchTrigger = () => {
    setArrayRefetch((prev) => prev + 1);
  };

  //DEBUG
  useEffect(() => {
    // logger.focus("INTERVIEW-ARRAY", interviewsArray);
    logger.focus("SNAPSHOT", interviewDataSnapshot);
    //logger.focus("REF", id, newInterviewRef?.current);
  }, [interviewDataSnapshot]);

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
    // logger.debug("Opening alert with message: ", openAlert);
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
    logger.focus("Sent Question: ", data);
    const response = await interview.sendInterviewQuestions(
      interviewDataSnapshot.id.value,
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

  const sendAnswers = async (id, data) => {
    logger.focus("Sent Answers: ", data);
    const response = await interview.sendInterviewAnswers(
      interviewDataSnapshot.id.value,
      data
    );
    if (response.success === true) {
      openAlertProc(response.message, response.severity);
      closeAlertWithDelay();
      setInterviewDataSnapshot(response.data);
      arrayRefetchTrigger();
      navigate(`/dashboard/interviews/details/${response.data.id}`);
    } else {
      openAlertProc(response.message, response.severity);
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

  const clickBackToPreview = () => {
    arrayRefetchTrigger();
    //NAV to interview preview
    navigate(`/dashboard/interviews/`);
  };

  const clickBackToDetails = () => {
    arrayRefetchTrigger();
    //NAV to interview preview
    navigate(`/dashboard/interviews/details/${id}`);
  };

  const clickOfferPreview = (id) => {
    //NAV to interview detail
    navigate(`/dashboard/interviews/details/${id}`);
    reloadSnapshot(id);
  };

  const fetchQuestions = async () => {
    try {
      await interview
        .getFormattedInterviewTemplateQuestions(
          interviewDataSnapshot.interviewTemplateID.value
        )
        .then((response) => {
          if (response.success) {
            //TODO change data format here if needed
            logger.focus("Fetched Questions:", response.data);
            setQuestions(response.data);
            openAlertProc(response.message, "success");
            closeAlertWithDelay();
          } else {
            openAlertProc(response.message, "error");
            closeAlertWithDelay();
            setQuesiton({});
          }
        });

      //has the student answered? if so there will be a quizID in the response
    } catch (e) {
      openAlertProc(e.message, "error");
      closeAlertWithDelay();
      throw e;
    }
  };

  const fetchAnswers = async () => {
    try {
      if (interviewDataSnapshot.hasAnswered.value === true) {
        await interview
          .getFormattedStudentAnswers(
            interviewDataSnapshot.interviewQuizID.value
          )
          .then((response) => {
            logger.debug("SONO DENTRO");
            if (response.success === true) {
              //TODO change data format here if needed
              logger.focus("Fetched answers:", response.data);
              setAnswers(response.data);
              openAlertProc(response.message, "success");
              closeAlertWithDelay();
            } else {
              openAlertProc(response.message, "error");
              closeAlertWithDelay();
            }
          })
          .catch((e) => {
            openAlertProc(e.message, "error");
            closeAlertWithDelay();
          });
      } else {
        setAnswers({});
      }
    } catch (e) {
      throw e;
    }
  };

  useEffect(() => {
    if (interviewDataSnapshot === null) return;
    fetchQuestions();
    fetchAnswers();
  }, [interviewDataSnapshot]);

  /**
   *
   * @returns {Promise<Response>} Fetches the interviews data from the backend and sets the {@link interviewsArray}
   */
  const fetchedData = async () => {
    return await interview.getFormattedInterviews().then((response) => {
      if (response.success === false) {
        logger.debug("Aray", response);
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
  }, [arrayRefetch]);

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
    }
  }, [id, interviewsArray]);

  const value = {
    interviewsArray,
    openAlert,
    interviewDataSnapshot,
    interviewID,
    reloadSnapshot,
    setInterviewDataSnapshot,
    arrayRefetchTrigger,
    questions,
    answers,
    sendInterview,
    clickBackToDetails,
    sendAnswers,
    openAlertProc,
    clickOfferPreview,
    clickBackToPreview,
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
