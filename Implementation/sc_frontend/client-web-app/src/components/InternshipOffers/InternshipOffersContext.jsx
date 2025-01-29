import React, { useCallback, useEffect } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import * as internshipOffer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";
import { Alert } from "@mui/material";

const InternshipOffersContext = React.createContext();

export const useInternshipOffersContext = () => {
  const context = React.useContext(InternshipOffersContext);
  if (!context) {
    throw new Error(
      "useInternshipOffersContext must be used within a InternshipOffersProvider"
    );
  }
  return context;
};

export const InternshipOffersProvider = ({ children }) => {
  const { profile } = useGlobalContext();

  const [offerData, setofferData] = React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  const handleOfferClick = (offer) => {
    console.log("Selected Offer:", offer);
    navigate(`/dashboard/internship-offer/internship-detail/${offer.id}`);
  };

  useEffect(() => {
    console.log("Profile:", profile);
    if (profile.userType != "COMPANY") {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User is not a company");
      console.log("User is not a company");
    }
    internshipOffer
      .getFormattedCompanyInternships(profile.userID)
      .then((response) => {
        if (response.success === false) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          setOfferData(response.data);
        }
      });
  }, []);

  const value = { handleOfferClick, offerData };

  return (
    <InternshipOffersContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
          <div style={{ margin: "20px 0" }}></div>
          <div style={{ display: "flex", justifyContent: "center" }}>
             {/* <SCIntOffersPreview offerData={offerData} /> */}
          </div>
        </>
      )}
      {children}
    </InternshipOffersContext.Provider>
  );
};
