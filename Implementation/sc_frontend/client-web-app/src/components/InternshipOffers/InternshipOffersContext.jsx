import React, { useCallback, useEffect, useState } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import * as internshipOffer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";
import { Alert } from "@mui/material";
import * as logger from "../../logger/logger";
import { useParams } from "react-router-dom";

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
  const { id } = useParams();

  const [offersArray, setOffersArray] = useState([]);
  const [offerDataSnapshot, setOfferDataSnapshot] = useState(null);
  const [openAlert, setOpenAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertSeverity, setAlertSeverity] = useState("success");

  const updateInternshipOffer = useCallback((id, data) => {
    //TODO logica per inviare il dato...la metto qua o nel component?
    setOffersArray((prev) => {
      return prev.map((item) => {
        if (item.id === id) {
          return { ...item, ...data };
        }
        return item;
      });
    });
  }, []);

  const reloadSnapshot = (OfferID) => {
    logger.debug("InternshipOffersArray", offersArray);
    const snapshot = offersArray.reduce((acc, item) => {
      if (item.id.value == OfferID) return { ...acc, ...item };
      return acc;
    }, {});
    if (Object.keys(snapshot).length === 0) {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("Offer not found");
    } else {
      setOpenAlert(false);
      setOfferDataSnapshot(snapshot);
    }
    logger.debug("InternshipOffer Snapshot", offerDataSnapshot);
  };

  useEffect(() => {
    logger.focus("Company Profile:", profile);
    if (profile.userType != "COMPANY") {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User is not a company");
      console.log("User is not a company");
    } else {
      internshipOffer
        .getFormattedCompanyInternships(profile.userID)
        .then((response) => {
          logger.focus("fetched internship offers", response);
          if (response.success === false) {
            setOpenAlert(true);
            setAlertSeverity(response.severity);
            setAlertMessage(response.message);
          } else {
            setOffersArray(response.data);
            setOpenAlert(false);
          }
        });
    }
  }, []);

  useEffect(() => {
    if (id === undefined || id === null || id === "") {
      return;
    }
    if (offersArray.length === 0) {
      return;
    }
    logger.debug("InternshipOffersArray", offersArray);
    const snapshot = offersArray.reduce((acc, item) => {
      if (item.id.value == id) return { ...acc, ...item };
      return acc;
    }, {});
    if (Object.keys(snapshot).length === 0) {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("Offer not found");
    } else {
      setOpenAlert(false);
      setOfferDataSnapshot(snapshot);
    }
    logger.debug("InternshipOffer Snapshot", offerDataSnapshot);
  }, [id, offersArray]);

  const value = {
    offersArray,
    openAlert,
    offerDataSnapshot,
    setOfferDataSnapshot,
    updateInternshipOffer,
    reloadSnapshot,
  };

  return (
    <InternshipOffersContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
        </>
      )}
      {children}
    </InternshipOffersContext.Provider>
  );
};
