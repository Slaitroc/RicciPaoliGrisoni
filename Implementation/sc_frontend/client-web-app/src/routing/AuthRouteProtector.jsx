import React from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Navigate } from "react-router-dom";

const AuthRouteProtector = ({
  children,
  redirectTo = "/signin",
  invertBehavior = false,
}) => {
  const { isAuthenticated } = useGlobalContext();

  // console.log(`Auth Check - isAuth: ${isAuthenticated}, invert: ${invertBehavior}`);

  if (invertBehavior) {
    return isAuthenticated ? <Navigate to={redirectTo} replace /> : children;
  }

  return isAuthenticated ? children : <Navigate to={redirectTo} replace />;
};

export default AuthRouteProtector;
