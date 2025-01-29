import React from "react";
import { Navigate } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";

const EmailRouteProtector = ({ children, invertBehavior = false }) => {
  const { isEmailVerified } = useGlobalContext();

  // Debug
  // console.log("Email verification status:", isEmailVerified);

  if (invertBehavior) {
    return isEmailVerified ? <Navigate to={"/dashboard"} replace /> : children;
  }

  return isEmailVerified ? (
    children
  ) : (
    <Navigate to={"/confirm-email"} replace />
  );
};

export default EmailRouteProtector;
