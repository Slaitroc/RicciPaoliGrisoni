import React from "react";
import { Navigate } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import PropTypes from "prop-types";
import { CircularProgress } from "@mui/material";

const UserCreationRouteProtector = ({ children }) => {
  const { profile, loading } = useGlobalContext();

  // Debug
  // console.log("Email verification status:", isEmailVerified);

  if (loading) return <CircularProgress size="3rem" />;

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
