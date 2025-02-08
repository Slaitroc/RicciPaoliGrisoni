import React, { useEffect, useState } from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Navigate, useLocation } from "react-router-dom";
import { LoadingPage } from "../pages/LoadingPage";
import * as logger from "../logger/logger";

const AuthRouteProtector = ({
  children,
  redirectTo = "/signin",
  invertBehavior = false,
}) => {
  const { isAuthenticated, loading, setLoading } = useGlobalContext();
  const location = useLocation();

  // logger.focus("AUTH- isAuthenticated:", isAuthenticated);
  // logger.focus("AUTH- Loading status:", loading);

  if (loading) return <LoadingPage />;
  else {
    if (invertBehavior) {
      return isAuthenticated ? <Navigate to={redirectTo} replace /> : children;
    }

    return isAuthenticated ? children : <Navigate to={redirectTo} replace />;
  }
};

export default AuthRouteProtector;
