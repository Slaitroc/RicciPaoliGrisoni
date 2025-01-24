import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signOut,
} from "firebase/auth";
import { auth } from "./firebaseConfig";

// REGISTER LOGIN



export const loginUser = async (email, password) => {
  try {
    const userCredential = await signInWithEmailAndPassword(
      auth,
      email,
      password
    );
    const token = await userCredential.user.getIdToken();
    console.log("User logged in. Token:", token);
    return token;
  } catch (error) {
    console.error("Error during login:", error.message);
    throw error;
  }
};
