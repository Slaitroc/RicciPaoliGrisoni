import React, { useEffect, useState } from "react";
import SCIntOffersPreview from "../components/InternshipOffers/SCIntOffersPreview";
import { useGlobalContext } from "../global/GlobalContext";
import { Alert } from "@mui/material";
import Card from "@mui/material/Card";
import { getFormattedCompanyInternships } from "../api-calls/api-wrappers/submission-wrapper/internshipOffer";
const InternshipOffers = () => {
  const { profile } = useGlobalContext();
  const [offerData, setOfferData] = useState(null);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  //When the component mounts, fetch the internship offers of this company
  useEffect(() => {
    console.log("Profile:", profile);
    if (profile.userType != "COMPANY") {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User is not a company");
      console.log("User is not a company");
    }
    getFormattedCompanyInternships(profile.userID).then((response) => {
      if(response.success === false){
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      }else{
        setOfferData(response.data);
      }
    });
  }, []);
  
  return (
    <>
      <Card variant="outlined">
        {openAlert && (
          <>
            <Alert severity={alertSeverity}>{alertMessage}</Alert>
            <div style={{ margin: '20px 0' }}></div>
            <div style={{ display: 'flex', justifyContent: 'center' }}>
              <SCIntOffersPreview offerData={offerData} />
            </div>
          </>
        )}
        {!openAlert && <SCIntOffersPreview offerData={offerData} />}      
      </Card>
    </>
  );
};

export default InternshipOffers;
