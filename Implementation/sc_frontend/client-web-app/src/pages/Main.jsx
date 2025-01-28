import React, { useEffect } from "react";
import SCBlog from "../components/Main/SCBlog";
import { useGlobalContext } from "../global/GlobalContext";
import { useNavigate } from "react-router-dom";
import * as account from "../api-calls/api-wrappers/account-wrapper/account.js";
import { onAuthStateChanged } from "firebase/auth";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig.js";

const Main = () => {
  const { isAuthenticated, setUserType} = useGlobalContext();
  const navigate = useNavigate();

  useEffect(() => {
    onAuthStateChanged(firebaseConfig.auth, (user) => {
      if(user){
        console.log("Main page useEffect - isAuthenticated");
        account.getUserData().then((response) => {
          console.log("Response:", response);
          console.log("Response status:", response.status);
          if (response.status === 204) {
            console.log("Dati utente non trovati. Creazione nuovo utente.");
            // Naviga alla pagina di creazione utente
            navigate("/signup/user-creation");
          }
          if (response.status === 200) {
            response.json().then((data) => {
              //TODO verifica che la mail sia confermata
              setUserType(data.properties.userType);
              setProfile(data.properties);
              console.log("Fetched User data:", data);
              // Naviga alla dashboard
              navigate("/dashboard");
            });
          }
        });
      }
    });
  }, [])

  


  return <SCBlog></SCBlog>;
};

export default Main;
