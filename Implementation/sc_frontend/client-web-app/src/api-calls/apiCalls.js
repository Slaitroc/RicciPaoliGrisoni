import { fetchWrapper } from "./fetchWrapper";
import { auth } from "./api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";

export const getToken = async () => {
  const user = auth.currentUser;
  if (!user) {
    console.error("Utente non autenticato. Nessun token disponibile.");
    return ""; // Token vuoto se l'utente non è autenticato
  }
  return await user.getIdToken();
};
// #region Global api calls

export const retrieveProfile = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/acc/private/retrieve-profile", {
    method: "GET",
    headers: { Authorization: `Bearer ${token}` },
  });
};

export const sendUserData = async (userType, userData) => {
  const token = await getToken();
  console.log(JSON.stringify({ userType: userType, ...userData }));
  console.log("Token: ", token);
  return fetchWrapper("/application-api/acc/private/send-user-data", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ userType: userType, ...userData }),
  });
};

// #endregion

// #region Notification api calls
export const sendNotificationToken = async (notificationToken) => {
  const token = await getToken();
  console.log("Token: ", token);
  return fetchWrapper("/notification-api/private/send-token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      // DANGER - può essere nullo il token?? significa che l'utente non è loggato
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ notificationToken }),
  });
};

// #endregion

// #region Authorization api calls

export const login = async (email, password) => {
  return fetchWrapper("/auth-api/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
};

export const sendEmailConfirmed = async (uuid) => {
  const token = await getToken();

  return fetchWrapper("/application-api/acc/private/confirm-user", {
    method: "POST",
    headers: { Authorization: `Bearer ${token}` },
    body: JSON.stringify({ uuid }),
  });
};
// #region test calls

export const testRequest = async () => {
  return fetchWrapper("/application-api/sub/private/internships/?companyID=8", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer 11111",
    },
  });
};

export const testHello = async () => {
  return fetchWrapper("/application-api/hello", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer 11111",
    },
  });
};

export const testProxy = async () => {
  return fetchWrapper("/application-api/private/test", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer 11111",
    },
  });
};

// #endregion
