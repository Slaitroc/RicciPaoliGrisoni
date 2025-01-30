import React, { useState } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import { Alert, Box, Button } from "@mui/material";
import { SCAddIcon } from "../Shared/SCIcons";
import * as spontaneousApplication from "../../api-calls/api-wrappers/submission-wrapper/spontaneousApplication";
import { useNavigate } from "react-router-dom";

const ApplicationContext = React.createContext();

export const useApplicationContext = () => {
  const context = React.useContext(ApplicationContext);
  if (!context) {
    throw new Error(
      "ApplicationContext must be used within a ApplicationContextProvider"
    );
  }
  return context;
};

export const clickOnApplication = (item) => {
  console.log("Clicked on application:", item);
};

export const ApplicationsProvider = ({ children }) => {
  const navigate = useNavigate();
  const { profile } = useGlobalContext();
  const [applicationData, setApplicationData] = useState(null);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  React.useEffect(() => {
    console.log("Profile from application:", profile);
    if (profile.userType == "UNIVERSITY") {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage(
        "User is a university, it is not allowed to have spontaneous applications"
      );
      console.log("User is a university");
    }
    spontaneousApplication
      .getFormattedSpontaneousApplications()
      .then((response) => {
        if (response.success === false) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          setApplicationData(response.data);
        }
      });
  }, []);

  const handleErrorButtonClick = () => {
    //NAV
    navigate("/dashboard/browse-internship-offers");
  };

  const value = {
    handleErrorButtonClick,
    applicationData,
    clickOnApplication,
  };

  return (
    <ApplicationContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
        </>
      )}
      {children}
      <div style={{ margin: "20px 0" }}></div>
      <div style={{ display: "flex", justifyContent: "center" }}>
        <Box paddingLeft={5}>
          <Button variant="outlined" onClick={handleErrorButtonClick}>
            Browse All Internship Offers
          </Button>
        </Box>
      </div>
    </ApplicationContext.Provider>
  );
};
