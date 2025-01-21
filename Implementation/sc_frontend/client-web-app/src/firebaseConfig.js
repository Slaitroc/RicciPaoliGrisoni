// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth } from "firebase/auth";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCk4Qsv67eQ_YDGdzdPVl19VlljqxNlOQU",
  authDomain: "student-and-companies.firebaseapp.com",
  projectId: "student-and-companies",
  storageBucket: "student-and-companies.firebasestorage.app",
  messagingSenderId: "890249519273",
  appId: "1:890249519273:web:ecc87749b667b5285989b8",
  measurementId: "G-98ZHR2SHE7"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

export const auth = getAuth(app);