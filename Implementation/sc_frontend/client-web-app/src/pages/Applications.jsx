import React, { useState } from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Alert } from "@mui/material";
import Card from "@mui/material/Card";
import { getFormattedSpontaneousApplications } from "../api-calls/api-wrappers/submission-wrapper/spontaneousApplication";
import { SCApplication } from "../components/Applications/SCApplications";
import { Button, Box } from "@mui/material";
import SCAddIcon from "@mui/icons-material/Add";

const Applications = () => {
  const { profile } = useGlobalContext();
  const [applicationData, setApplicationData] = useState(null);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  //when the component mounts, fetch the applications
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
    getFormattedSpontaneousApplications(profile.userID).then((response) => {
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
    //todo navigate to the browse internship page
    console.log("Navigate to the browse internship page");
  };

  const handleSpontaneousClick = (profile) => {
    console.log("Selected Application:", profile);
  };

  return (
    <>
      <Card variant="outlined">
        {openAlert && (
          <>
            <Alert severity={alertSeverity}>{alertMessage}</Alert>
            <div style={{ margin: "20px 0" }}></div>
            <div style={{ display: "flex", justifyContent: "center" }}>
              <Box paddingLeft={5}>
                <Button
                  startIcon={<SCAddIcon />}
                  variant="outlined"
                  onClick={handleErrorButtonClick}
                >
                  Browse Internship Offers
                </Button>
              </Box>
            </div>
          </>
        )}
        {!openAlert && (
          <SCApplication
            applicationData={applicationData}
            buttonAction={handleSpontaneousClick}
          />
        )}
      </Card>
    </>
  );
};

export default Applications;
