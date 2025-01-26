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
  equals = false,
  navigateTo = "/dashboard",
}) => {

  const { isAuthenticated } = useGlobalContext();
  return isAuthenticated == equals ? <Navigate to={navigateTo} /> : children;
};

RouteProtector.propTypes = {
  children: PropTypes.node.isRequired,
  equals: PropTypes.bool,
  navigateTo: PropTypes.string,
};

export default RouteProtector;
