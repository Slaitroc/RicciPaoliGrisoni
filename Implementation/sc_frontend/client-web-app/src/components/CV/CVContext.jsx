import React, { useContext, useEffect } from "react";
import * as cv from "../../api-calls/api-wrappers/submission-wrapper/cv";
import * as logger from "../../logger/logger";
import { Alert } from "@mui/material";
import { useGlobalContext } from "../../global/GlobalContext";

const CVContext = React.createContext();

export const useCVContext = () => {
  const context = useContext(CVContext);
  if (!context) {
    throw new Error("useCVContext must be used within a CVProvider");
  }
  return context;
};

export const CVProvider = ({ children }) => {
  const { profile } = useGlobalContext();

  const [cvData, setCvData] = React.useState({
    studentID: {
      serverField: "studentID",
      label: "Student ID",
      value: "",
    },
    id: {
      serverField: "id",
      label: "Curriculum ID",
      value: "",
    },
    studentName: {
      serverField: "studentName",
      label: "Student Name",
      value: profile.name + " " + profile.surname,
    },
    contacts: {
      serverField: "contacts",
      label: "Contacts",
      value: "Update with your infos!",
    },
    spokenLanguages: {
      serverField: "spokenLanguages",
      label: "Spoken Language",
      value: "Update with your infos!",
    },
    education: {
      serverField: "education",
      label: "Education",
      value: "Update with your infos!",
    },
    certifications: {
      serverField: "certifications",
      label: "Certifications",
      value: "Update with your infos!",
    },
    workExperiences: {
      serverField: "workExperiences",
      label: "Work Experience",
      value: "Update with your infos!",
    },
    updateTime: {
      serverField: "updateTime",
      label: "Last Update",
      value: null,
    },
    project: {
      serverField: "project",
      label: "Projects",
      value: "Update with your infos!",
    },
    skills: {
      serverField: "skills",
      label: "Skills",
      value: "Update with your infos!",
    },
  });
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");
  useEffect(() => {
    logger.debug("CVProvider mounted");
    cv.getStudentCV(profile.userID)
      .then((response) => {
        if (response.success === false) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          if (response.data.cv === null) {
            setOpenAlert(true);
            setAlertSeverity(response.severity);
            setAlertMessage(response.message);
          } else {
            setOpenAlert(false);
            setCvData(response.data.cv);
          }
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
    setOpenAlert,
    setAlertMessage,
    setAlertSeverity,
  };

  return (
    <CVContext.Provider value={value}>
      {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}
      {children}
    </CVContext.Provider>
  );
};
