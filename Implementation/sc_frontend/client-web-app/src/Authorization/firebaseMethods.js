import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signOut,
} from "firebase/auth";
import { auth } from "./firebaseConfig";

// REGISTER LOGIN

export const registerUser = async (email, password) => {
  console.log("User Registration Started");
  try {
    const userCredential = await createUserWithEmailAndPassword(
      auth,
      email,
      password
    );
    const token = await userCredential.user.getIdToken();
    console.log("User registered. Token:", token);
    return token;
  } catch (error) {
    console.error("Error during registration:", error.message);
    throw error;
  }
};

// export const loginUser = async (email, password) => {
//   try {
//     const userCredential = await signInWithEmailAndPassword(
//       auth,
//       email,
//       password
//     );
//     const token = await userCredential.user.getIdToken();
//     console.log("User logged in. Token:", token);
//     return token;
//   } catch (error) {
//     console.error("Error during login:", error.message);
//     throw error;
//   }
// };

export const logoutUser = async () => {
  try {
    const result = await signOut(auth);
    console.log("User logged out successfully!");
    return result;
  } catch (error) {
    console.error("Error during logout:", error.message);
    throw error;
  }
};
