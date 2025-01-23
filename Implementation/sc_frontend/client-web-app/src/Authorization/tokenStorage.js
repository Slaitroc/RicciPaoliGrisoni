// TOKEN METHODS

export const saveToken = (token) => {
  localStorage.setItem("authToken", token); // Salva in localStorage
  console.log("Token Saved");
};

export const getToken = () => {
  return localStorage.getItem("authToken"); // Recupera da localStorage
};

export const clearToken = () => {
  localStorage.removeItem("authToken"); // Rimuove il token
};
