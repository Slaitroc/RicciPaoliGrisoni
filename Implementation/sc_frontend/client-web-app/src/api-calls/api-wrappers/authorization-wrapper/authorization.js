import * as apiCalls from "../../apiCalls";
import * as firebaseAuth from "firebase/auth";
import { auth } from "./firebase-utils/firebaseConfig";
import { BASE_DOMAIN } from "../../apiConfig";

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
    // DEBUG
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
      //DANGER - url di reindirizzamento nella email di verifica
      //url: `${BASE_DOMAIN}:5174/email-verified`, //DANGER TO CHANGE IF PC CHANGE
      url: `http://localhost:5174/email-verified`, //DANGER TO CHANGE IF PC CHANGE
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
    // DEBUG
    // console.error("Error during registration:", err.message);
    if (err.code === "auth/email-already-in-use") {
      return {
        status: 500,
        ok: false,
        json: async () => ({
          properties: {
            error: "Email alrready in use",
          },
        }),
        text: async () =>
          JSON.stringify({
            properties: {
              error: "Email alrready in use",
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

// export const registerOld = async (userData, password) => {
//   try {
//     // Registra l'utente con email e password
//     await firebaseAuth.createUserWithEmailAndPassword(
//       auth,
//       userData.email,
//       password
//     );
//     const token = await auth.currentUser.getIdToken();

//     // Invia la mail di verifica e imposta la procedura di verifica
//     const actionCodeSettings = {
//       //DANGER - url di reindirizzamento nella email di verifica
//       url: `${BASE_DOMAIN}:5173/email-verified`,
//       handleCodeInApp: true, // Indica che l'app gestirà l'URL
//     };
//     firebaseAuth.sendEmailVerification(auth.currentUser, actionCodeSettings);
//     return response;
//   } catch (err) {
//     // DEBUG
//     //console.error("Error during registration:", err.message);
//     if (err.code === "auth/email-already-in-use") {
//       return new Promise((resolve, reject) => {
//         try {
//           // Simulazione di un errore server (status 500)
//           const response = {
//             status: 500,
//             ok: false, // ok è false per status >= 400
//             json: async () => "Email already in use", // Corpo in formato JSON
//             text: async () => "Email already in use", // Corpo come stringa
//           };

//           // Rifiuta la Promise con la risposta simulata
//           reject(response);
//         } catch (err) {
//           // Gestione di eventuali errori durante la creazione della risposta
//           reject({
//             status: 500,
//             ok: false,
//             json: async () => ({ error: err.message || "Unknown error" }),
//             text: async () => err.message || "Unknown error",
//           });
//         }
//       });
//     }
//     return new Promise((resolve, reject) => {
//       try {
//         // Simulazione di un errore server (status 500)
//         const response = {
//           status: 500,
//           ok: false, // ok è false per status >= 400
//           json: async () => err.message, // Corpo in formato JSON
//           text: async () => err.message, // Corpo come stringa
//         };

//         // Rifiuta la Promise con la risposta simulata
//         reject(response);
//       } catch (err) {
//         // Gestione di eventuali errori durante la creazione della risposta
//         reject({
//           status: 500,
//           ok: false,
//           json: async () => ({ error: err.message || "Unknown error" }),
//           text: async () => err.message || "Unknown error",
//         });
//       }
//     });
//   }
// };
