import React, { useEffect } from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Outlet, useNavigate } from "react-router-dom";
import * as account from "../api-calls/api-wrappers/account-wrapper/account.js";
import { onAuthStateChanged } from "firebase/auth";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig.js";
import * as logger from "../logger/logger.js";

export const RouteBase = () => {
  const { isAuthenticated, setUserType, setProfile, setIsEmailVerified } =
    useGlobalContext();
  const navigate = useNavigate();

  useEffect(() => {
    onAuthStateChanged(firebaseConfig.auth, (user) => {
      if (user) {
        account.getUserData().then((response) => {
          if (response.status === 400) {
            logger.debug("- ROUTE BASE: Token mancante nella richiesta.");
          }
          if (response.status === 204) {
            logger.debug(
              "- ROUTE BASE: Dati utente non trovati. Creazione nuovo utente."
            );
            setIsEmailVerified(false);
            // Naviga alla pagina di creazione utente
            //FIX //navigate("/signup/user-creation");
          }
          if (response.status === 200) {
            response.json().then((data) => {
              logger.debug("- ROUTE BASE: Fetched User data:", data);
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
      }
    });
  }, []);

  return <Outlet></Outlet>;
};
