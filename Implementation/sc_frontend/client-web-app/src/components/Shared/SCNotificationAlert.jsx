import React from "react";
import ReactDOM from "react-dom";
import { Alert, styled } from "@mui/material";
import { useGlobalContext } from "../../global/GlobalContext";

// Contenitore per impilare le notifiche
const NotificationsContainer = styled("div")(({ theme }) => ({
  position: "fixed",
  top: "10px", // Posizione iniziale in alto
  right: "10px", // Allinea a destra
  zIndex: 9999, // Assicurati che stia sopra tutto
  display: "flex",
  flexDirection: "column", // Disposizione verticale
  gap: "10px", // Spaziatura tra le notifiche
}));

// Stile personalizzato per ogni notifica
const CustomStyledAlert = styled(Alert)(({ theme }) => ({
  width: "300px", // Larghezza uniforme per tutte le notifiche
  backgroundColor: "#4caf50", // Sfondo verde
  color: "#ffffff", // Testo bianco
  fontWeight: "bold", // Testo in grassetto
  boxShadow: theme.shadows[4], // Ombra
}));

const SCNotificationAlert = () => {
  const { notification } = useGlobalContext(); // Supponiamo che `notifications` sia un array

  return ReactDOM.createPortal(
    <>
      <NotificationsContainer>
        {notification.map((notification, index) => (
          <CustomStyledAlert key={index}>
            {`Notifica: ${notification.data.title} - ${notification.data.body}`}
          </CustomStyledAlert>
        ))}
      </NotificationsContainer>
    </>,
    document.body // Monta l'intero contenitore nel body
  );
};

// const SCNotificationAlert = () => {
//   const { showNotification, notifications } = useGlobalContext();

//   return ReactDOM.createPortal(
//     <>
//       {showNotification && (
//         <CustomStyledAlert>{notifications}</CustomStyledAlert>
//       )}
//     </>,
//     document.body // Monta l'alert nel body
//   );
// };

export default SCNotificationAlert;
