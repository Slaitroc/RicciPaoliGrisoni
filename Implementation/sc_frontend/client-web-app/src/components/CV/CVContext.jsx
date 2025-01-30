import React, { useContext, useEffect } from "react";
import * as cv from "../../api-calls/api-wrappers/submission-wrapper/cv";
import * as logger from "../../logger/logger";
import { Alert } from "@mui/material";

const CVContext = React.createContext();

export const useCVContext = () => {
  const context = useContext(CVContext);
  if (!context) {
    throw new Error("useCVContext must be used within a CVProvider");
  }
  return context;
};

export const CVProvider = ({ children }) => {
  const [cvData, setCvData] = React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    logger.debug("CVProvider mounted");
    cv.getStudentCV()
      .then((response) => {
        if (response.success === false) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          setOpenAlert(false);
          setCVData(response.data);
        }
      })
      .catch((error) => {
        console.error("Error during getStudentCV:", error);
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("Error while fetching CV data...");
      });
    return () => {
      logger.debug("CVProvider unmounted");
    };
  }, []);

  const value = {
    cvData,
    setCvData,
  };

  return (
    <CVContext.Provider value={value}>
      {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}
      {children}
    </CVContext.Provider>
  );
};
