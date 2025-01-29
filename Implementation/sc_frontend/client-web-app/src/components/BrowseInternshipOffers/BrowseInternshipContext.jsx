import React, { createContext, useEffect } from "react";
import * as internshipOffer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";
import { Alert } from "@mui/material";

const BrowseInternshipContext = createContext();

export const useBrowseInternshipContext = () => {
  const context = React.useContext(BrowseInternshipContext);
  if (!context) {
    throw new Error(
      "useBrowseInternshipContext must be used within a BrowseInternshipProvider"
    );
  }
  return context;
};

export const clickOnOffer = (offer) => {
  console.log("Offer Clicked:", offer);
};

export const BrowseInternshipOffersProvider = ({ children }) => {
  const [offerData, setOfferData] = React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    internshipOffer.getFormattedInternships().then((response) => {
      if (response.success === false) {
        console.error(response.message);
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      } else {
        setOfferData(response.data);
      }
    });
  }, []);

  const value = {
    offerData,
    openAlert,
    clickOnOffer,
  };

  return (
    <BrowseInternshipContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
        </>
      )}
      {children}
    </BrowseInternshipContext.Provider>
  );
};
