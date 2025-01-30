import React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import { LoadingPage } from "../pages/LoadingPage";

const EmailRouteProtector = ({ children, invertBehavior = false }) => {
  const { isEmailVerified, loading, setLoading } = useGlobalContext();

  // Debug
  console.log("EMAIL- Email verification status:", isEmailVerified);
  console.log("EMAIL- Loading status:", loading);

  if (loading) return <LoadingPage/>;
  else {
    if (invertBehavior) {
      return isEmailVerified ? (
        <Navigate to={"/dashboard"} replace />
      ) : (
        children
      );
    }

    return isEmailVerified ? (
      children
    ) : (
      <Navigate to={"/confirm-email"} replace />
    );
  }
};

export default EmailRouteProtector;
