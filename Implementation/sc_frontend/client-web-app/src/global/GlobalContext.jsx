import React, { useContext, useState, useEffect, useCallback } from "react";
import * as global from "./globalStatesInit";

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
  const [uuid, setUuid] = useState("");
  const [profile, setProfile] = useState(global.INIT_PROFILE);
  const [loading, setLoading] = useState(global.INIT_LOADING);
  const [error, setError] = useState(global.INIT_ERROR);
  const [userType, setUserType] = useState(global.INIT_USER_TYPE);

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
        uuid,
        setUuid,
        setUserType,
        setIsAuthenticated,
        setProfile,
        handleFileChange,
        removePhoto,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
