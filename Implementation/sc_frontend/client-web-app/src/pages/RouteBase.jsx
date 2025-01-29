import React, { useEffect } from "react";
import SCBlog from "../components/Main/SCBlog";
import { useGlobalContext } from "../global/GlobalContext";
import { Outlet, useNavigate } from "react-router-dom";
import * as account from "../api-calls/api-wrappers/account-wrapper/account.js";
import { onAuthStateChanged } from "firebase/auth";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig.js";
import Main from "./Main.jsx";

export const RouteBase = () => {
  const { isAuthenticated, setUserType, setProfile } = useGlobalContext();
  const navigate = useNavigate();

  useEffect(() => {
    onAuthStateChanged(firebaseConfig.auth, (user) => {
      if (user) {
        console.log("Main page useEffect - isAuthenticated");
        account.getUserData().then((response) => {
          console.log("Response:", response);
          if (response.status === 204) {
            console.log("Dati utente non trovati. Creazione nuovo utente.");
            // Naviga alla pagina di creazione utente
            navigate("/signup/user-creation");
          }
          if (response.status === 200) {
            response.json().then((data) => {
              console.log("Fetched User data:", data);
              setUserType(data.properties.userType);
              setProfile(data.properties);
              // Naviga alla dashboard
              //TODO verifica che la mail sia confermata
              navigate("/dashboard");
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
