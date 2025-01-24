importScripts(
  "https://www.gstatic.com/firebasejs/9.17.1/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/9.17.1/firebase-messaging-compat.js"
);

// Importa la configurazione di Firebase
const firebaseConfig = {
  apiKey: "AIzaSyCk4Qsv67eQ_YDGdzdPVl19VlljqxNlOQU",
  authDomain: "student-and-companies.firebaseapp.com",
  projectId: "student-and-companies",
  storageBucket: "student-and-companies.firebasestorage.app",
  messagingSenderId: "890249519273",
  appId: "1:890249519273:web:ecc87749b667b5285989b8",
  measurementId: "G-98ZHR2SHE7",
};

// Inizializza Firebase
firebase.initializeApp(firebaseConfig);

// Inizializza Firebase Messaging
const messaging = firebase.messaging();

// Gestisce i messaggi in background
messaging.onBackgroundMessage((payload) => {
  console.log("[Service Worker] Messaggio ricevuto in background:", payload);

  const notificationTitle = payload.notification?.title || "Nuova Notifica!";
  const notificationOptions = {
    body: payload.notification?.body || "Hai un nuovo messaggio.",
    icon: payload.notification?.icon || "/default-icon.png",
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});

// Gestisce il clic sulle notifiche
self.addEventListener("notificationclick", (event) => {
  console.log("[Service Worker] Notifica cliccata:", event);
  event.notification.close();

  // Apri una finestra o fai altre azioni
  event.waitUntil(
    clients.openWindow(event.notification?.data?.click_action || "/")
  );
});
