import React, { createContext, useContext, useEffect } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import * as logger from "../../logger/logger";
import { getFormattedInterviewTemplates } from "../../api-calls/api-wrappers/Interview/interviewTemplate";
import { Alert } from "@mui/material";
const InterviewTemplateContext = createContext();

export const useInterviewTemplateContext = () => {
  const context = useContext(InterviewTemplateContext);
  if (!context) {
    throw new Error(
      "useInternshipOffersContext must be used within a InternshipOffersProvider"
    );
  }
  return context;
};

export const InterviewTemplateProvider = ({ children }) => {
  const { profile } = useGlobalContext();
  const [interviewTemplateData, setInterviewTemplateData] = React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    getFormattedInterviewTemplates().then((response) => {
      //logger.debug("response: ", response);
      if (response.success) {
        setInterviewTemplateData(response.data);
        //logger.debug("interviewTemplateData: ", interviewTemplateData);
      } else {
        setOpenAlert(true);
        setAlertMessage(response.message);
        setAlertSeverity(response.severity);
      }
    });
  }, []);

  const value = {
    interviewTemplateData,
    openAlert,
    setInterviewTemplateData,
    setOpenAlert,
    setAlertMessage,
    setAlertSeverity,
  };

  return (
    <InterviewTemplateContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert onClose={() => setOpenAlert(false)} severity={alertSeverity}>
            {alertMessage}
          </Alert>
        </>
      )}
      {children}
    </InterviewTemplateContext.Provider>
  );
};
