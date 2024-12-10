import React, { useContext, useState, useEffect } from "react";
import { fakeFetchUserData, fakeLogin } from "./fake_backend/fakeBackend";

const GlobalContext = React.createContext();

export const useGlobalContext = () => {
  const context = useContext(GlobalContext);
  console.log("Context value:", context);
  if (!context) {
    throw new Error("useGlobalContext must be used within a GlobalProvider");
  }
  return context;
};

export const GlobalProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  console.log(isAuthenticated);

  const login = async (email, password) => {
    setLoading(true);
    setError(null);
    try {
      const { token, userId } = await fakeLogin(email, password);
      localStorage.setItem("token", token);
      localStorage.setItem("userId", userId);
      setIsAuthenticated(true);
      const userProfile = await fakeFetchUserData(userId);
      setProfile(userProfile);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    setIsAuthenticated(false);
    setProfile(null);
  };

  const fetchProfile = async (userId) => {
    setLoading(true);
    try {
      const userProfile = await fakeFetchUserData(userId);
      setProfile(userProfile);
    } catch {
      setError("Failed To Fetch Profile");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    if (token && userId) {
      setIsAuthenticated(true);
      fetchProfile(userId); //TODO da gestire tramite react-query
    }
  }, []);

  return (
    <GlobalContext.Provider
      value={{
        isAuthenticated,
        profile,
        loading,
        error,
        login,
        logout,
        fetchProfile,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
