import { fetchWrapper } from "./fetchWrapper";

export const login = async (email, password) => {
  return fetchWrapper("/auth-api/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
};

export const retrieveProfile = async (token) => {
  return fetchWrapper("/application-api/account/private/retrieve-profile", {
    method: "GET",
    headers: { Authorization: `Bearer ${token}` },
  });
};

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
