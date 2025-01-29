import React, { useContext, useEffect } from "react";

const CVContext = React.createContext();

export const useCVContext = () => {
  const context = useContext(CVContext);
  if (!context) {
    throw new Error("useCVContext must be used within a CVProvider");
  }
  return context;
};

export const CVProvider = ({ children }) => {


  useEffect(() => {
    // DEBUG
    console.log("CVProvider mounted");
    


    
    
    return () => {
      console.log("CVProvider unmounted");
    };
  }, []);  

  const value = {};


  return <CVContext.Provider value={value}>{children}</CVContext.Provider>;
};
