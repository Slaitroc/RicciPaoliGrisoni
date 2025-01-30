// UserTypeRouteProtector.jsx
import React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";
import { CircularProgress } from "@mui/material";
import PropTypes from "prop-types";
import {
  INIT_USER_TYPE,
  STUDENT_USER_TYPE,
  COMPANY_USER_TYPE,
  UNIVERSITY_USER_TYPE,
} from "../global/globalStatesInit";

const UserTypeRouteProtector = ({ children, allowedTypes }) => {
  const { userType, loading } = useGlobalContext();

  if (loading) return <CircularProgress size="3rem" />;
  else {
    if (userType === INIT_USER_TYPE) {
      return <div>Loading user type...</div>;
    }

    if (!allowedTypes.includes(userType)) {
      return <Navigate to="/dashboard" replace />;
    }

    return children;
  }
};

UserTypeRouteProtector.propTypes = {
  allowedTypes: PropTypes.arrayOf(PropTypes.string).isRequired,
  children: PropTypes.node.isRequired,
};

export default UserTypeRouteProtector;
