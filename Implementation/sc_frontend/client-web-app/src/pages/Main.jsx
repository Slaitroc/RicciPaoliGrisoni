import React, { useEffect } from "react";
import SCBlog from "../components/Main/SCBlog";
import { useGlobalContext } from "../global/GlobalContext";
import { useNavigate } from "react-router-dom";
import * as account from "../api-calls/api-wrappers/account-wrapper/account.js";

const Main = () => {
  const { setNavigateReference, navigateReference, isAuthenticated, setUserType } = useGlobalContext();
  const navigate = useNavigate();

  useEffect(() => {
    setNavigateReference(navigate);
  }, []);

  useEffect(() => {
      if(isAuthenticated){
        account.getUserData().then((response) => {
          if (response.status === 204) {
            console.log("Dati utente non trovati. Creazione nuovo utente.");
            // Naviga alla pagina di creazione utente
            if (navigator) navigateReference("/signup/user-creation");
            else console.error("Navigator not found.");
          } else if (response.ok) {
            response.json().then((data) => {
              //TODO verifica che la mail sia confermata
              setUserType(data.properties.userType);
              setProfile(data.properties);
              console.log("Fetched User data:", data);
              // Naviga alla dashboard
              if (navigator) navigateReference("/dashboard");
              else console.error("Navigator not found.");
            });
          }
        });
      }
  }, [isAuthenticated]);
  return <SCBlog></SCBlog>;
};

export default Main;
