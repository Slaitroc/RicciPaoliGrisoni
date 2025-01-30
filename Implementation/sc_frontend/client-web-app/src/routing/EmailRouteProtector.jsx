import React from "react";
import { Navigate } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import { CircularProgress } from "@mui/material";

const EmailRouteProtector = ({ children, invertBehavior = false }) => {
  const { isEmailVerified, loading, setLoading } = useGlobalContext();

  // Debug
  // console.log("Email verification status:", isEmailVerified);

  if (loading) return <CircularProgress size="3rem" />;

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
