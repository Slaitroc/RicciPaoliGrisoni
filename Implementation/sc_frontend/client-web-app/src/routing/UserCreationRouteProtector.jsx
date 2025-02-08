import React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import PropTypes from "prop-types";
import { LoadingPage } from "../pages/LoadingPage";
import * as logger from "../logger/logger";

const UserCreationRouteProtector = ({ children, invertBehavior = false }) => {
  const { profile, loading } = useGlobalContext();

  // logger.focus("USER- Profile status:", profile);
  // logger.focus("USER- Loading status:", loading);

  if (loading) return <LoadingPage />;
  else if (invertBehavior) {
    return profile === null ? children : <Navigate to={"/dashboard"} replace />;
  } else {
    return profile === null ? (
      <Navigate to={"/signup/user-creation"} replace />
    ) : (
      children
    );
  }
};

UserCreationRouteProtector.propTypes = {
  children: PropTypes.node.isRequired,
};

export default UserCreationRouteProtector;
