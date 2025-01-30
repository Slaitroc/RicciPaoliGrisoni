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
      } else {
      }
    });
  }, []);

  return <Outlet></Outlet>;
};
