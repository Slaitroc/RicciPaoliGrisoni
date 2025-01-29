import React from "react";
import { useGlobalContext } from "../global/GlobalContext";
import PropTypes from "prop-types";
import { Navigate } from "react-router-dom";

/**
 * RouteProtector component to protect routes based on authentication status.
 *
 * @param {Object} props - The component props.
 * @param {React.ReactNode} props.children - The child components to render if the condition is met.
 * @param {boolean} props.equals - The expected authentication status to allow access.
 * @param {string} props.navigateTo - The path to navigate to if the condition is not met.
 * @returns {React.ReactNode} - The children if the condition is met, otherwise a <Navigate /> component.
 */
const RouteProtector = ({
  children,
  isAuth = false,
  authNavigateTo = "/dashboard",
  emailCheck = false,
}) => {
  const { isAuthenticated, isEmailVerified } = useGlobalContext();

  let navigateTo;
  if (isAuth === true && emailCheck === true) {
    if (isEmailVerified === false) {
      navigateTo = "/confirm-email";
    } else {
      navigateTo = authNavigateTo;
    }
  } else {
    navigateTo = authNavigateTo;
  }
  if (authNavigateTo === "none") isAuth = !isAuthenticated;
  return isAuthenticated == isAuth ? <Navigate to={navigateTo} /> : children;
};

RouteProtector.propTypes = {
  children: PropTypes.node.isRequired,
  isAuth: PropTypes.bool,
  emailCheck: PropTypes.bool,
  authNavigateTo: PropTypes.string,
};

export default RouteProtector;
