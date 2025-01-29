import React from "react";
import { Navigate } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import PropTypes from "prop-types";

const UserCreationRouteProtector = ({ children }) => {
  const { profile } = useGlobalContext();

  // Debug
  // console.log("Email verification status:", isEmailVerified);

  console.log("Profile:", profile);

  return profile === null ? (
    <Navigate to={"/signup/user-creation"} replace />
  ) : (
    children
  );
};

UserCreationRouteProtector.propTypes = {
  children: PropTypes.node.isRequired,
};

export default UserCreationRouteProtector;
