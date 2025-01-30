import React, { createContext, useEffect, useState } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import { Alert } from "@mui/material";
import * as interview from "../../api-calls/api-wrappers/Interview/interview";
const InterviewContext = createContext();

export const useInterviewContext = () => {
  const context = React.useContext(InterviewContext);
  if (!context) {
    throw new Error(
      "useInternshipOffersContext must be used within a InternshipOffersProvider"
    );
  }
  return context;
};

export const clickOnInterview = (interview) => {
  console.log("Interview Clicked:", interview);
};

export const InterviewProvider = ({ children }) => {
  const { profile } = useGlobalContext();

  const [interviewData, setInterviewData] = React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    if (profile.userType != "STUDENT" && profile.userType != "COMPANY") {
      console.log("User is not a student or company");
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User is not a student or company");
    } else {
      interview.getFormattedInterviews(profile.userID).then((response) => {
        if (response.status === false) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          setInterviewData(response.data);
        }
      });
    }
  }, []);

  const value = {
    interviewData,
    openAlert,
    clickOnInterview,
  };

  return (
    <InterviewContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
        </>
      )}
      {children}
    </InterviewContext.Provider>
  );
};
