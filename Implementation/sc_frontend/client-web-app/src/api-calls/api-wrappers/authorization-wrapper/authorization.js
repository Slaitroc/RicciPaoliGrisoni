import * as apiCalls from "../../apiCalls";
import * as firebaseAuth from "firebase/auth";
import { auth } from "./firebase-utils/firebaseConfig";
import { FRONTEND_DOMAIN } from "../../apiConfig";
import * as logger from "../../../logger/logger";

export const login = async (email, password) => {
  try {
    const userCredentials = await firebaseAuth.signInWithEmailAndPassword(
      auth,
      email,
      password
    );
    console.log("User logged in successfully!");
    const token = await userCredentials.user.getIdToken();
    // DEBUG log
    console.log(token);
    return {
      status: 200,
      ok: false,
      json: async () => ({
        properties: {
          message: "Successful login",
        },
      }),
      text: async () =>
        JSON.stringify({
          properties: {
            message: "Successful login",
          },
        }),
    };
  } catch (err) {
    return {
      status: 500,
      ok: false,
      json: async () => ({
        properties: {
          error: err.message || "Unknown error",
        },
      }),
      text: async () =>
        JSON.stringify({
          properties: {
            error: err.message || "Unknown error",
          },
        }),
    };
  }
};

export const logout = async () => {
  try {
    const result = await firebaseAuth.signOut(auth);
    console.log("User logged out successfully!");
    return {
      status: 200,
      ok: false,
      json: async () => ({
        properties: {
          message: "User logged out successfully!",
        },
      }),
      text: async () =>
        JSON.stringify({
          properties: {
            error: "User logged out successfully!",
          },
        }),
    };
  } catch (err) {
    enableLogger
    //console.error("Error during logout:", err.message);
    return {
      status: 500,
      ok: false,
      json: async () => ({
        properties: {
          error: err.message || "Unknown error",
        },
      }),
      text: async () =>
        JSON.stringify({
          properties: {
            error: err.message || "Unknown error",
          },
        }),
    };
  }
};

export const register = async (email, password) => {
  try {
    console.log("Registering user with email:", email);
    // Registra l'utente con email e password
    await firebaseAuth.createUserWithEmailAndPassword(auth, email, password);
    const token = await auth.currentUser.getIdToken();

    console.log("User registered successfully!");

    // Invia email di verifica
    const actionCodeSettings = {
      // url di reindirizzamento nella email di verifica
      //DANGER TO CHANGE IF PC CHANGE
      url: `${FRONTEND_DOMAIN}/email-verified`,
      handleCodeInApp: true, // Indica che l'app gestirà l'URL
    };
    await firebaseAuth.sendEmailVerification(
      auth.currentUser,
      actionCodeSettings
    );
    return {
      status: 201,
      ok: false,
      json: async () => ({
        properties: {
          message: "Successful user registration",
        },
      }),
      text: async () =>
        JSON.stringify({
          properties: {
            message: "Successful user registration",
          },
        }),
    };
  } catch (err) {
    logger.error("Error during registration:", err.message);
    if (err.code === "auth/email-already-in-use") {
      return {
        status: 500,
        ok: false,
        json: async () => ({
          properties: {
            error: "Email already in use",
          },
        }),
        text: async () =>
          JSON.stringify({
            properties: {
              error: "Email already in use",
            },
          }),
      };
    }
    return {
      status: 500,
      ok: false,
      json: async () => ({
        properties: {
          error: err.message || "Unknown error",
        },
      }),
      text: async () =>
        JSON.stringify({
          properties: {
            error: err.message || "Unknown error",
          },
        }),
    };
  }
};
