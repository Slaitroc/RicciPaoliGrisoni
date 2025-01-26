import * as apiCalls from "../../apiCalls";
import * as firebaseAuth from "firebase/auth";
import { auth } from "./firebase-utils/firebaseConfig";
import { BASE_DOMAIN } from "../../apiConfig";

/**
 * Logs in a user with the provided email and password.
 *
 * @param {string} email - The email of the user.
 * @param {string} password - The password of the user.
 * @returns {Promise<Object>} A promise that resolves to an object containing:
 *   - {number} code - The response code:
 *     - 200: Login successful, profile and token returned.
 *     - 204: No matching credentials.
 *     - 400: Bad request, invalid login credentials.
 *     - 401: Unauthorized, invalid token for profile retrieval.
 *     - 500: Internal server error during profile retrieval.
 *   - {Object} [profile] - The user's profile (if login is successful).
 *   - {string} [token] - The authentication token (if login is successful).
 *   - {Object} [loginResponse] - The response object from the login API call.
 *   - {Object} [userProfileResponse] - The response object from the profile retrieval API call.
 * @throws {Error} If an unexpected error occurs during login.
 *
 * @returns {Promise<Object>} A promise that resolves to an object containing the status code and response data.
 * @throws {Error} Throws an error if the login process fails unexpectedly.
 */
// export const loginv1 = async (email, password) => {
//   try {
//     const loginResponse = await apiCalls.login(email, password);

//     if (loginResponse.status === 204) {
//       return { code: 204, loginResponse };
//     }

//     if (loginResponse.status === 400) {
//       return { code: 400, loginResponse };
//     }

//     if (loginResponse.ok && loginResponse.status != 204) {
//       const res = await loginResponse.json();
//       const token = res.token;
//       tokenStorage.saveToken(token);

//       const userProfileResponse = await apiCalls.retrieveProfile(token);

//       if (userProfileResponse.ok) {
//         const profile = await userProfileResponse.json();
//         // console.log("profile: ", profile);
//         return { code: 200, profile, token };
//       } else {
//         if (userProfileResponse.status === 401) {
//           return { code: 401, userProfileResponse };
//         }

//         if (userProfileResponse.status === 500) {
//           return { code: 500, userProfileResponse };
//         }
//       }
//     } else {
//       throw new Error("Failed to login: unexpected response");
//     }
//   } catch (err) {
//     console.error("Unexpected error during login has been thrown");
//     throw err;
//   }
// };

export const login = async (email, password) => {
  try {
    const userCredentials = await firebaseAuth.signInWithEmailAndPassword(
      auth,
      email,
      password
    );
    const token = await userCredentials.user.getIdToken();
    apiCalls.retrieveProfile(token);
    return { code: 200, token };
  } catch (error) {
    console.error("Error during login:", error.message);
    const message = error.message;
    return { code: 400, message };
  }
};

/**
 * Logs out the current user by calling the logoutUser method from firebaseMethods.
 * Clears the stored token upon successful logout.
 *
 * @async
 * @function logout
 * @returns {Promise<Object>} A promise that resolves to an object containing a status code and the response or error.
 * @property {number} code - The status code of the operation (200 for success, 500 for error).
 * @property {Object} response - The response object from the logoutUser method on success.
 * @property {Object} err - The error object on failure.
 */
export const logout = async () => {
  try {
    const result = await firebaseAuth.signOut(auth);
    console.log("User logged out successfully!");
    return { code: 200, result };
  } catch (error) {
    console.error("Error during logout:", error.message);
    return { code: 500, error };
  }
};

/**
 * Register a user of the specified type with the provided email and password. Sends the user data to the server. Sends a confirmation link to the user email to  validate the email address and unlock the account.
 *
 * @param {string} userType - The type of the user to register: student/company/university.
 * @param {string} userData - The data of the user to register.
 * @param {string} password - The password for the user to register.
 * @returns {Promise<Object>} A promise that resolves to an object containing:
 * - code: {number} The HTTP status code (200 for success, 400 for invalid credentials, 500 for server error).
 * - response: {string} A message indicating the result of the registration (only for code 400).
 * - token: {string} The authentication token (only for code 200).
 * - err: {Error} The error object (only for code 500).
 */
export const register = async (userType, userData, password) => {
  console.log("ciao");
  try {
    await firebaseAuth.createUserWithEmailAndPassword(
      auth,
      userData.email,
      password
    );
    const realToken = await auth.currentUser.getIdToken()
    console.log("real Token" + realToken);
    // TODO aggiungi dati utente da inviare
    const response = await apiCalls.sendUserData(userType, userData);
    if (response.status === 400) {
      const body = await response.json();
      console.log(body.properties.error);
      return { code: 400, response };
    }

    const actionCodeSettings = {
      //DANGER - url di reindirizzamento nella email di verifica
      url: `${BASE_DOMAIN}:5173/email-verified`,
      handleCodeInApp: true, // Indica che l'app gestirà l'URL
    };
    firebaseAuth.sendEmailVerification(auth.currentUser, actionCodeSettings);

    const token = auth.currentUser.getIdToken();
    return { code: 200, token, userData };
  } catch (err) {
    throw err;
  }
};
