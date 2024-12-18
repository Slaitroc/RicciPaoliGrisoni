import React, { useContext, useState, useEffect, useCallback } from "react";
import { fakeFetchUserData, fakeLogin } from "../fake_backend/fakeBackend";
import * as global from "../global/globalStatesInit";

const GlobalContext = React.createContext();

export const useGlobalContext = () => {
  const context = useContext(GlobalContext);
  //console.log("Context value:", context);
  if (!context) {
    throw new Error("useGlobalContext must be used within a GlobalProvider");
  }
  return context;
};

export const GlobalProvider = ({ children }) => {
  // #region AuthContext
  const [isAuthenticated, setIsAuthenticated] = useState(
    global.INIT_IS_AUTHENTICATED
  );
  const [profile, setProfile] = useState(global.INIT_PROFILE);
  const [loading, setLoading] = useState(global.INIT_LOADING);
  const [error, setError] = useState(global.INIT_ERROR);
  const [userType, setUserType] = useState(global.INIT_USER_TYPE);

  const login = useCallback(async (email, password) => {
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
  }, []);

  const logout = useCallback(() => {
    console.log("logout viene eseguita");
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    setIsAuthenticated(false);
    setProfile(null);
  }, []);

  // #region debug
  // const prevLogout = useRef();
  // useEffect(() => {
  //   console.log("Render Global Provider");
  //   prevLogout.current === logout
  //     ? console.log("stesso riferimento")
  //     : console.log("riferimento cambiato");
  //   prevLogout.current = logout;
  // });

  // #endregion debug

  const fetchProfile = useCallback(async (userId) => {
    setLoading(true);
    try {
      const userProfile = await fakeFetchUserData(userId);
      setProfile(userProfile);
    } catch {
      setError("Failed To Fetch Profile");
    } finally {
      setLoading(false);
    }
  }, []);

  // #endregion AuthContext

  // #region FileUploadContext
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewUrl, setPreviewUrl] = useState("");

  const handleFileChange = useCallback((event) => {
    const file = event.target.files[0];
    if (file) {
      setSelectedFile(file);
      // Crea un URL temporaneo per visualizzare l'immagine
      setPreviewUrl(URL.createObjectURL(file));
    }
  }, []);

  const removePhoto = useCallback(() => {
    setSelectedFile(null);
    setPreviewUrl("");
  }, []);

  // #endregion FileUploadContext

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
        userType,
        selectedFile,
        previewUrl,
        setUserType,
        login,
        logout,
        fetchProfile,
        handleFileChange,
        removePhoto,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
