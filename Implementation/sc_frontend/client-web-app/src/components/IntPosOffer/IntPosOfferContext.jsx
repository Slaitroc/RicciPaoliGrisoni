import * as intPosOffer from "../../api-calls/api-wrappers/Interview/intPosOffer";
import React, { createContext, useEffect } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import Alert from "@mui/material/Alert";
import * as logger from "../../logger/logger";
const IntPosOfferContext = createContext();

export const useIntPosOfferContext = () => {
  const context = React.useContext(IntPosOfferContext);
  if (!context) {
    throw new Error(
      "useIntPosOfferContext must be used within a IntPosOfferProvider"
    );
  }
  return context;
};

export const IntPosOfferProvider = ({ children }) => {
  const { profile } = useGlobalContext();

  const [intPosOfferData, setIntPosOfferData] = React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    if (profile.userType != "STUDENT" && profile.userType != "COMPANY") {
      logger.error("User is not a student or a company");
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User is not a student or a company");
    } else {
      intPosOffer
        .getFormattedInterviewPosOffers(profile.userID)
        .then((response) => {
          if (response.success === false) {
            logger.focus("error", response);
            setOpenAlert(true);
            setAlertSeverity(response.severity);
            setAlertMessage(response.message);
          } else if (response.success == true) {
            if (response.data === null) {
              setOpenAlert(true);
              setAlertSeverity("info");
              setAlertMessage("No internship position offers found");
              setIntPosOfferData([]);
            } else {
              setIntPosOfferData(response.data);
              setOpenAlert(false);
            }
          }
        });
    }
  }, []);

  const value = {
    setIntPosOfferData,
    intPosOfferData,
    setOpenAlert,
    setAlertMessage,
    setAlertSeverity,
  };

  return (
    <IntPosOfferContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert onClose={() => setOpenAlert(false)} severity={alertSeverity}>
            {alertMessage}
          </Alert>
        </>
      )}
      {children}
    </IntPosOfferContext.Provider>
  );
};
