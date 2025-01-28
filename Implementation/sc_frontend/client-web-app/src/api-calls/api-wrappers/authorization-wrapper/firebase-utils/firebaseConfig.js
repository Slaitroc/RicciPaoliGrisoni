// Importa le librerie necessarie
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getMessaging } from "firebase/messaging";

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

