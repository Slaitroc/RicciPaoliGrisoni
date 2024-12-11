import React from "react";
import { useGlobalContext } from "../globalContext";
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
const RouteProtector = ({ children, props }) => {
  const { isAuthenticated } = useGlobalContext();
  return isAuthenticated === props.equals ? (
    children
  ) : (
    <Navigate to={props.navigateTo} />
  );
};

RouteProtector.propTypes = {
  children: PropTypes.isRequired,
  props: {
    equals: PropTypes.bool,
    navigateTo: PropTypes.string.isRequired,
  },
};

// default behavior: if the user is authenticated (isAuthenticated === false) returns the children, otherwise navigate to Home
// if the user is already logged in he can't reach the signup/signin pages
RouteProtector.defaultProps = {
  props: {
    equals: false,
    navigateTo: "/",
  },
};

export default RouteProtector;
