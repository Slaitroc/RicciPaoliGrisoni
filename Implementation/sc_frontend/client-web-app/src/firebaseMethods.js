import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth } from "./firebaseConfig";

export const registerUser = async (email, password) => {
  try {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    const token = await userCredential.user.getIdToken();
    console.log("User registered. Token:", token);
    return token;
  } catch (error) {
    console.error("Error during registration:", error.message);
    throw error;
  }
};

export const loginUser = async (email, password) => {
    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const token = await userCredential.user.getIdToken();
      console.log("User logged in. Token:", token);
      return token;
    } catch (error) {
      console.error("Error during login:", error.message);
      throw error;
    }
  };


// TOKEN METHODS

export const saveToken = (token) => {
  localStorage.setItem("authToken", token); // Salva in localStorage
};

export const getToken = () => {
  return localStorage.getItem("authToken"); // Recupera da localStorage
};

export const clearToken = () => {
  localStorage.removeItem("authToken"); // Rimuove il token
};
