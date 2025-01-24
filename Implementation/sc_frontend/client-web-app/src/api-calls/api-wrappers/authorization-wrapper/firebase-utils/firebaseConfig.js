// Importa le librerie necessarie
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

// Configurazione Firebase
export const firebaseConfig = {
  apiKey: "AIzaSyCk4Qsv67eQ_YDGdzdPVl19VlljqxNlOQU",
  authDomain: "student-and-companies.firebaseapp.com",
  projectId: "student-and-companies",
  storageBucket: "student-and-companies.firebasestorage.app",
  messagingSenderId: "890249519273",
  appId: "1:890249519273:web:ecc87749b667b5285989b8",
  measurementId: "G-98ZHR2SHE7",
};

// Inizializza Firebase
const app = initializeApp(firebaseConfig);

// Inizializza Auth
export const auth = getAuth(app);

// Inizializza Messaging
export const messaging = getMessaging(app);

// Registra il Service Worker per FCM
if ("serviceWorker" in navigator) {
  navigator.serviceWorker
    .register("/firebase-messaging-sw.js")
    .then((registration) => {
      console.log("Service Worker registrato con successo:", registration);

      // Ottieni il token FCM
      getToken(messaging, {
        vapidKey:
          "BC7y3aEdX7NmWTt2tmdW1uV7lCgC52PooEkUiOXS5yQP3SLCV97jcpFkVg3JhOL9sCI9kPsrW1JaIDULKHEW0o8",
        serviceWorkerRegistration: registration,
      })
        .then((currentToken) => {
          if (currentToken) {
            console.log("Token FCM:", currentToken);
            console.log("Token FCM:", messaging.getToken());
            apiCalls.sendNotificationToken(messaging.getToken());
          } else {
            console.log("Nessun token FCM disponibile.");
          }
        })
        .catch((err) => {
          console.error("Errore durante l'ottenimento del token FCM:", err);
        });
    })
    .catch((error) => {
      console.error(
        "Errore durante la registrazione del Service Worker:",
        error
      );
    });
}

// Gestisce le notifiche in foreground
onMessage(messaging, (payload) => {
  console.log("Messaggio ricevuto in foreground:", payload);
  // Mostra una notifica o aggiorna l'interfaccia utente
  alert(
    `Notifica: ${payload.notification.title} - ${payload.notification.body}`
  );
});
