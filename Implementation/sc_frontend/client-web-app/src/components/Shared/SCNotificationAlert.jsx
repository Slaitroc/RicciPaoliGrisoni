import React from "react";
import ReactDOM from "react-dom";
import { Alert } from "@mui/material";
import { useGlobalContext } from "../../global/GlobalContext";

const SCNotificationAlert = () => {
  const { showNotification, notificationMessage } = useGlobalContext();

  return ReactDOM.createPortal(
    <>
      {showNotification && <Alert severity="info">{notificationMessage}</Alert>}
    </>,
    document.body // Monta l'alert direttamente nel body
  );
};

export default SCNotificationAlert;
