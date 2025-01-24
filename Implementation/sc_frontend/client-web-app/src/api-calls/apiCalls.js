import { fetchWrapper } from "./fetchWrapper";
import { auth } from "./api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";

// #region Global api calls

export const retrieveProfile = async () => {
  return fetchWrapper("/application-api/account/private/retrieve-profile", {
    method: "GET",
    headers: { Authorization: `Bearer ${auth.currentUser.getIdToken()}` },
  });
};

// #endregion

// #region Notification api calls
export const sendNotificationToken = async (notificationToken) => {
  return fetchWrapper("/notification-api/private/send-token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${auth.currentUser.getIdToken()}`,
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
