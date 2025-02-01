import React, {
  useEffect,
  useState,
  useContext,
  createContext,
  useRef,
} from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import { Alert } from "@mui/material";
import { useParams } from "react-router-dom";
import * as logger from "../../logger/logger";
import * as interview from "../../api-calls/api-wrappers/Interview/interview";

const InterviewsContext = createContext();

export const useInterviewsContext = () => {
  const context = useContext(InterviewsContext);
  if (!context) {
    throw new Error(
      "useInterviewsContext must be used within a InterviewsProvider"
    );
  }
  return context;
};

export const InterviewsProvider = ({ children }) => {
  const { profile } = useGlobalContext();
  const { id } = useParams();
  const newOfferRef = useRef({});

  const [offersArray, setOffersArray] = useState([]);
  const [offerDataSnapshot, setOfferDataSnapshot] = useState(null);
  const [openAlert, setOpenAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertSeverity, setAlertSeverity] = useState("success");

  const [forceRender, setForceRender] = useState(0);

  //DEBUG
  useEffect(() => {
    logger.focus("OFFER-ARRAY", offersArray);
    logger.focus("SNAPSHOT", offerDataSnapshot);
    logger.focus("REF", id, newOfferRef?.current);
  }, [offersArray, offerDataSnapshot, forceRender]);

  const updateInterview = (id, data) => {
    // interview.sendUpdateMyOffer(data);
    // setOffersArray((prev) => {
    //   return prev.map((item) => {
    //     if (item.id === id) {
    //       return { ...item, ...data };
    //     }
    //     return item;
    //   });
    // });
    reloadSnapshot(id);
  };

  const reloadSnapshot = (OfferID) => {
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
  };

  useEffect(() => {
    const fetchedData = async () => {
      if (profile.userType != "COMPANY") {
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("User is not a company");
        console.log("User is not a company");
        return;
      } else {
        // return await interview
        //   .getFormattedCompanyInternships(profile.userID)
        //   .then((response) => {
        //     if (response.success === false) {
        //       setOpenAlert(true);
        //       setAlertSeverity(response.severity);
        //       setAlertMessage(response.message);
        //     } else {
        //       setOffersArray(response.data);
        //       setOpenAlert(false);
        //     }
        //   });
      }
    };

    fetchedData();
  }, []);

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
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("Offer not found");
    } else {
      setOpenAlert(false);
      setOfferDataSnapshot(snapshot);
      newOfferRef.current = snapshot;
    }
  }, [id, offersArray]);

  const value = {
    offersArray,
    openAlert,
    offerDataSnapshot,
    setOfferDataSnapshot,
    updateInterview,
    reloadSnapshot,
    newOfferRef,
    setForceRender,
  };

  return (
    <InterviewsContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
        </>
      )}
      {children}
    </InterviewsContext.Provider>
  );
};
