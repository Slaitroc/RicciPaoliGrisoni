import * as communication from "../../api-calls/api-wrappers/communication-wrapper/communication";
import * as internshipPositionOffer from "../../api-calls/api-wrappers/Interview/intPosOffer";
import React, { createContext, useEffect } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import * as logger from "../../logger/logger";
import Alert from "@mui/material/Alert";

const CommunicationsContext = createContext();

export const useCommunicationsContext = () => {
  const context = React.useContext(CommunicationsContext);
  if (!context) {
    throw new Error(
      "useCommunicationsContext must be used within a CommunicationProvider"
    );
  }
  return context;
};

export const CommunicationsProvider = ({ children }) => {
  const { profile } = useGlobalContext();

  const [communicationsData, setCommunicationsData] = React.useState([]);
  const [internshipPositionOffers, setInternshipPositionOffers] =
    React.useState([]);
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    communication
      .getFormattedCommunications()
      .then((response) => {
        if (!response.success) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          logger.log("Communications fetched successfully");
          logger.log(response.data);
          setCommunicationsData(response.data);
        }
      })
      .catch((error) => {
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("Error fetching communications");
      });
    internshipPositionOffer
      .getFormattedInterviewPosOffers()
      .then((response) => {
        if (!response.success) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          logger.log("Internship position offers fetched successfully");
          logger.log(response.data);
          setInternshipPositionOffers(response.data);
        }
      })
      .catch((error) => {
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("Error fetching internship position offers");
      });
  }, []);

  const value = {
    communicationsData,
    openAlert,
    internshipPositionOffers,
  };

  return (
    <CommunicationsContext.Provider value={value}>
      {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}
      {children}
    </CommunicationsContext.Provider>
  );
};
