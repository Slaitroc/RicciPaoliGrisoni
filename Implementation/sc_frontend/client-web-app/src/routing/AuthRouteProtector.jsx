import React, { useEffect, useState } from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Navigate, useLocation } from "react-router-dom";
import { CircularProgress } from "@mui/material";

const AuthRouteProtector = ({
  children,
  redirectTo = "/signin",
  invertBehavior = false,
}) => {
  const { isAuthenticated, loading, setLoading } = useGlobalContext();
  const location = useLocation();

  console.log("AUTH- isAuthenticated:", isAuthenticated);
  console.log("AUTH- Loading status:", loading);

  if (loading) return <CircularProgress size="3rem" />;
  else {
    if (invertBehavior) {
      return isAuthenticated ? <Navigate to={redirectTo} replace /> : children;
    }

    return isAuthenticated ? children : <Navigate to={redirectTo} replace />;
  }
};

export default AuthRouteProtector;
