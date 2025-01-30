import React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import PropTypes from "prop-types";
import { LoadingPage } from "../pages/LoadingPage";

const UserCreationRouteProtector = ({ children }) => {
  const { profile, loading } = useGlobalContext();

  // Debug
  console.log("USER- Profile status:", profile);
  console.log("USER- Loading status:", loading);

  if (loading) return <LoadingPage/>;
  else {
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
