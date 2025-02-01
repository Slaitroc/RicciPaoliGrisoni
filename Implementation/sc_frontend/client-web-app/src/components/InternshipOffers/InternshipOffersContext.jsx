import React, {
  useEffect,
  useState,
  useContext,
  createContext,
  useRef,
} from "react";
import { useNavigate } from "react-router-dom";
import { useGlobalContext } from "../../global/GlobalContext";
import { Alert } from "@mui/material";
import * as logger from "../../logger/logger";
import { useParams } from "react-router-dom";
import * as internshipOffer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";

const InternshipOffersContext = createContext();

export const useInternshipOffersContext = () => {
  const context = useContext(InternshipOffersContext);
  if (!context) {
    throw new Error(
      "useInternshipOffersContext must be used within a InternshipOffersProvider"
    );
  }
  return context;
};

export const InternshipOffersProvider = ({ children }) => {
  const navigate = useNavigate();
  const { profile } = useGlobalContext();
  const { id } = useParams();
  const newOfferRef = useRef({});

  const [offersArray, setOffersArray] = useState([]);
  const [offerDataSnapshot, setOfferDataSnapshot] = useState(null);
  const [alertClose, setAlertClose] = useState(false);
  const [openAlert, setOpenAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertSeverity, setAlertSeverity] = useState();

  const [forceRender, setForceRender] = useState(0);

  const closeAlertWithDelay = () => {
    if (!alertClose) {
      setAlertClose(true);
      setTimeout(() => {
        setOpenAlert(false);
      }, 3000);
    }
  };

  const openAlertProc = () => {
    setAlertClose(false);
    setOpenAlert(true);
  };

  //DEBUG
  useEffect(() => {
    logger.focus("OFFER-ARRAY", offersArray);
    logger.focus("SNAPSHOT", offerDataSnapshot);
    logger.focus("REF", id, newOfferRef?.current);
  }, [offersArray, offerDataSnapshot, forceRender]);

  const updateInternshipOffer = async (id, data) => {
    const response = await internshipOffer.sendUpdateMyOffer(data);
    logger.focus("RESPONSE", response);
    if (response.success === true) {
      await fetchedData();
      setAlertSeverity(response.severity);
      setAlertMessage(response.message);
      openAlertProc();

      reloadSnapshot(response.data.id);
      //navigate(`/dashboard/internship-offers/details/${response.data.id}`);
    } else {
      setAlertSeverity(response.severity);
      setAlertMessage(response.message);
      closeAlertWithDelay();
    }
  };

  const createInternshipOffer = async (data) => {
    const response = await internshipOffer.sendUpdateMyOffer(data);
    if (response.success === true) {
      logger.log("SONO DENTRO");
      await fetchedData();
      setAlertSeverity(response.severity);
      setAlertMessage(response.message);
      openAlertProc();
      closeAlertWithDelay();
      reloadSnapshot(response.data.id);
      navigate(`/dashboard/internship-offers/details/${response.data.id}`);
    } else {
      setAlertSeverity(response.severity);
      setAlertMessage(response.message);
      openAlertProc();
    }
  };

  const reloadSnapshot = (OfferID) => {
    const snapshot = offersArray.reduce((acc, item) => {
      if (item.id.value == OfferID) return { ...acc, ...item };
      return acc;
    }, {});
    if (Object.keys(snapshot).length === 0) {
      openAlertProc();
      setAlertSeverity("error");
      setAlertMessage("Offer not found");
    } else {
      closeAlertWithDelay();
      setOfferDataSnapshot(snapshot);
    }
  };

  useEffect(() => {
    fetchedData();
  }, []);
  const fetchedData = async () => {
    if (profile.userType != "COMPANY") {
      openAlertProc();
      setAlertSeverity("error");
      setAlertMessage("User is not a company");
      console.log("User is not a company");
      return;
    } else {
      return await internshipOffer
        .getFormattedCompanyInternships(profile.userID)
        .then((response) => {
          if (response.success === false) {
            openAlertProc();
            setAlertSeverity(response.severity);
            setAlertMessage(response.message);
          } else {
            setOffersArray(response.data);
            closeAlertWithDelay();
          }
        });
    }
  };

  useEffect(() => {
    if (id === undefined || id === null || id === "") {
      return;
    }
    if (offersArray.length === 0) {
      return;
    }
    const snapshot = offersArray.reduce((acc, item) => {
      if (item.id.value == id) return { ...acc, ...item };
      return acc;
    }, {});
    if (Object.keys(snapshot).length === 0) {
      openAlertProc();
      setAlertSeverity("error");
      setAlertMessage("Offer not found");
    } else {
      closeAlertWithDelay();

      setOfferDataSnapshot(snapshot);
      newOfferRef.current = snapshot;
    }
  }, [id, offersArray]);

  const value = {
    offersArray,
    openAlert,
    offerDataSnapshot,
    setOfferDataSnapshot,
    updateInternshipOffer,
    reloadSnapshot,
    newOfferRef,
    setForceRender,
    createInternshipOffer,
    openAlertProc,
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
