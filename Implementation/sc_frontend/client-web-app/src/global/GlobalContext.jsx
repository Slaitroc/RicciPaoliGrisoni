import * as global from "./globalStatesInit";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";
import * as apiCalls from "../api-calls/apiCalls";
import React, { useContext, useState, useEffect, useCallback } from "react";
import { onAuthStateChanged } from "firebase/auth";
import { getToken, onMessage } from "firebase/messaging";
import * as logger from "../logger/logger";

const GlobalContext = React.createContext();

export const useGlobalContext = () => {
  const context = useContext(GlobalContext);
  if (!context) {
    throw new Error("useGlobalContext must be used within a GlobalProvider");
  }
  return context;
};

export const GlobalProvider = ({ children }) => {
  // NOTIFICATION
  const [showNotification, setShowNotification] = useState(false);
  const [notification, setNotification] = useState([]);

  // AUTHENTICATION
  const [isAuthenticated, setIsAuthenticated] = useState(
    global.INIT_IS_AUTHENTICATED
  );
  const [isEmailVerified, setIsEmailVerified] = useState(false);

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
    logger.debug("GlobalProvider mounted");
    logger.log("Service Worker registration...");
    navigator.serviceWorker
      .register("/firebase-messaging-sw.js")
      .then((registration) => {
        //logger.log("Service Worker registrato:", registration);
        logger.log("Service Worker registrato.");
        return getToken(firebaseConfig.messaging, {
          vapidKey:
            "BC7y3aEdX7NmWTt2tmdW1uV7lCgC52PooEkUiOXS5yQP3SLCV97jcpFkVg3JhOL9sCI9kPsrW1JaIDULKHEW0o8",
          serviceWorkerRegistration: registration,
        });
      })
      .then((currentToken) => {
        if (currentToken) {
          logger.log("Token FCM:", currentToken);
          token = currentToken;
          //NOTE Qui inviamo il token ogni volta perché il backend se già esiste non lo salva
        } else {
          logger.log("Nessun token FCM disponibile.");
        }
      })
      .catch((error) => {
        logger.error("Errore durante l'ottenimento del token FCM:", error);
      });

    onAuthStateChanged(firebaseConfig.auth, (user) => {
      if (user) {
        logger.log("Utente autenticato:", user.email);
        setIsAuthenticated(true);
        // Controlla se l'email è stata verificata
        if (!user.emailVerified) {
          logger.log("Email non verificata con firebase.");
        } else {
          logger.log("Email verificata.");
          setIsEmailVerified(true);
        }
        // Invia il token FCM al server
        if (token) {
          // FIX crea wrapper notification
          logger.log("FCM token:", token);
          apiCalls.sendNotificationToken(token).then((response) => {
            if (!response.ok) {
              logger.log("Errore durante l'invio del token FCM:", response);
            } else {
              logger.log("Token FCM inviato con successo.");
            }
          });
        }
      } else {
        logger.log("Nessun utente autenticato. Token FCM non inviato.");
        setIsAuthenticated(false);
      }
    });
    // Gestisce le notifiche in foreground
    onMessage(firebaseConfig.messaging, (payload) => {
      logger.log("Messaggio ricevuto in foreground:", payload);
      // Mostra una notifica o aggiorna l'interfaccia utente
      setNotification((prev) => [...prev, payload]);
      // Rimuove automaticamente la notifica dopo 5 secondi
      setTimeout(() => {
        setNotification((prev) => prev.filter((n) => n !== payload));
      }, 5000);
      // alert(
      //   `Notifica: ${payload.notification.title} - ${payload.notification.body}`
      // );
    });

    return () => {
      logger.debug("GlobalProvider unmounted");
    };
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
    notification,
    isEmailVerified,
    setNotification,
    setShowNotification,
    setIsEmailVerified,
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
