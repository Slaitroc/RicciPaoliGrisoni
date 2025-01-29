import React, { useEffect } from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Outlet, useNavigate } from "react-router-dom";
import * as account from "../api-calls/api-wrappers/account-wrapper/account.js";
import { onAuthStateChanged } from "firebase/auth";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig.js";

export const RouteBase = () => {
  const { isAuthenticated, setUserType, setProfile, setIsEmailVerified } =
    useGlobalContext();
  const navigate = useNavigate();

  useEffect(() => {
    onAuthStateChanged(firebaseConfig.auth, (user) => {
      if (user) {
        console.log("Main page useEffect - isAuthenticated");
        account.getUserData().then((response) => {
          console.log("Response:", response);
          if (response.status === 204) {
            console.log("Dati utente non trovati. Creazione nuovo utente.");
            setIsEmailVerified(false);
            // Naviga alla pagina di creazione utente
            //FIX //navigate("/signup/user-creation");
          }
          if (response.status === 200) {
            response.json().then((data) => {
              console.log("Fetched User data:", data);
              setUserType(data.properties.userType);
              setProfile(data.properties);
              if (data.properties.validate) setIsEmailVerified(true);
              else setIsEmailVerified(false);

              // Naviga alla dashboard
              //TODO verifica che la mail sia confermata
              // FIX//navigate("/dashboard");
            });
          }
        });
      } else {
        console.log("Main page useEffect - not isAuthenticated");
      }
    });
  }, []);

  return <Outlet></Outlet>;
};
