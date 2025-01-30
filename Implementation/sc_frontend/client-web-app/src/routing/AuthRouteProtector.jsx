import React, { useEffect, useState } from "react";
import { useGlobalContext } from "../global/GlobalContext";
import { Navigate } from "react-router-dom";
import { CircularProgress } from "@mui/material";

const AuthRouteProtector = ({
  children,
  redirectTo = "/signin",
  invertBehavior = false,
}) => {
  const { isAuthenticated, loading, setLoading } = useGlobalContext();

  // console.log("loading", loading);

  if (loading) return <CircularProgress size="3rem" />;

  // setLoading(true);

  if (invertBehavior) {
    return isAuthenticated ? <Navigate to={redirectTo} replace /> : children;
  }

  return isAuthenticated ? children : <Navigate to={redirectTo} replace />;
};

export default AuthRouteProtector;
