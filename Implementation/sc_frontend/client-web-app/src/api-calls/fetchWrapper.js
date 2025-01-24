import { BASE_DOMAIN } from "./apiConfig";

export const fetchWrapper = async (url, options = {}) => {
  console.log("api call to endpoint: ", `${BASE_DOMAIN}${url}`);
  const fullUrl = `${BASE_DOMAIN}${url}`;
  return await fetch(fullUrl, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
  });
};
