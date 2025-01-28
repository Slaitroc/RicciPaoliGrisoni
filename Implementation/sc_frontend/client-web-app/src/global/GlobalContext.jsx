import React, { useContext, useState, useEffect, useCallback } from "react";
import * as global from "./globalStatesInit";
import { onAuthStateChanged } from "firebase/auth";
import { getToken, onMessage } from "firebase/messaging";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";
import * as apiCalls from "../api-calls/apiCalls";
import * as account from "../api-calls/api-wrappers/account-wrapper/account";
import SCNotificationAlert from "../components/Shared/SCNotificationAlert";
import { RouterProvider } from "react-router-dom";
import router from "../routing/routers";
import { use } from "react";

const GlobalContext = React.createContext();

export const useGlobalContext = () => {
  const context = useContext(GlobalContext);
  //console.log("Context value:", context);
  if (!context) {
    throw new Error("useGlobalContext must be used within a GlobalProvider");
  }
  return context;
};

export const GlobalProvider = ({ children }) => {
  
  // NOTIFICATION
  const [showNotification, setShowNotification] = useState(false);
  const [notificationMessage, setNotificationMessage] = useState("");

  // AUTHENTICATION
  const [isAuthenticated, setIsAuthenticated] = useState(
    global.INIT_IS_AUTHENTICATED
  );

  // PROFILE
  const [profile, setProfile] = useState(global.INIT_PROFILE);
  const [userType, setUserType] = useState(null);

  // LOADING & ERROR
  const [loading, setLoading] = useState(global.INIT_LOADING);
  const [error, setError] = useState(global.INIT_ERROR);

  // #region FileUploadContext
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState("");

  const handleFileChange = useCallback((event) => {
    const file = event.target.files[0];
    if (file) {
      setSelectedFile(file);
      // Crea un URL temporaneo per visualizzare l'immagine
      setPreviewUrl(URL.createObjectURL(file));
    }
  }, []);

  const removePhoto = useCallback(() => {
    setSelectedFile(null);
    setPreviewUrl("");
  }, []);
  // #endregion FileUploadContext

  useEffect(() => {
    // NOTIFICATION & AUTHENTICATION
    // Registra il Service Worker e ottieni il token FCM
    let token = null;
    console.log("Service Worker supportato dal browser.");
    navigator.serviceWorker
      .register("/firebase-messaging-sw.js")
      .then((registration) => {
        console.log("Service Worker registrato:", registration);
        return getToken(firebaseConfig.messaging, {
          vapidKey:
            "BC7y3aEdX7NmWTt2tmdW1uV7lCgC52PooEkUiOXS5yQP3SLCV97jcpFkVg3JhOL9sCI9kPsrW1JaIDULKHEW0o8",
          serviceWorkerRegistration: registration,
        });
      })
      .then((currentToken) => {
        if (currentToken) {
          console.log("Token FCM:", currentToken);
          token = currentToken;
          //NOTE Qui inviamo il token ogni volta perché il backend se già esiste non lo salva
        } else {
          console.log("Nessun token FCM disponibile.");
        }
      })
      .catch((error) => {
        console.error("Errore durante l'ottenimento del token FCM:", error);
      });

    onAuthStateChanged(firebaseConfig.auth, (user) => {
      if (user) {
        console.log("Utente autenticato:", user.email);
        setIsAuthenticated(true);
        // Controlla se l'utente è stato creato
        // Invia il token FCM al server
        if (token) {
          // FIX crea wrapper notification
          console.log("FCM token:", token);
          apiCalls.sendNotificationToken(token).then((response) => {
            if (!response.ok) {
              console.error("Errore durante l'invio del token FCM:", response);
            } else {
              console.log("Token FCM inviato con successo.");
            }
          });
        }
      } else {
        console.log("Nessun utente autenticato. Token FCM non inviato.");
        setIsAuthenticated(false);
      }
    });
    // Gestisce le notifiche in foreground
    onMessage(firebaseConfig.messaging, (payload) => {
      console.log("Messaggio ricevuto in foreground:", payload);
      // Mostra una notifica o aggiorna l'interfaccia utente
      setShowNotification(true);
      setNotificationMessage(
        `Notifica: ${payload.notification.title} - ${payload.notification.body}`
      );
      setTimeout(() => {
        setShowNotification(false);
      }, 5000);
      // alert(
      //   `Notifica: ${payload.notification.title} - ${payload.notification.body}`
      // );
    });
  }, []);

  const value = {
    isAuthenticated,
    profile,
    loading,
    error,
    userType,
    selectedFile,
    previewUrl,
    showNotification,
    notificationMessage,
    setNotificationMessage,
    setShowNotification,
    setUserType,
    setIsAuthenticated,
    setProfile,
    handleFileChange,
    removePhoto,
  };

  return (
    <GlobalContext.Provider value={value}>{children}</GlobalContext.Provider>
  );
};
