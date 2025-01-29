import React, {useEffect} from "react";
import PropTypes from "prop-types";
import { useGlobalContext } from "../global/GlobalContext";
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
  requireAuth = false,
  redirectTo = "/signin",
  requireEmailVerification = false,
}) => {
  const { isAuthenticated, isEmailVerified } = useGlobalContext();

  let routeComponent;
  let routePath;
  let pass
  // console.log("requireAuth", requireAuth);
  // console.log("requireEmailVerification", requireEmailVerification);
  
  useEffect(() => {
    console.log("isAuthenticated", isAuthenticated);
    console.log("isEmailVerified", isEmailVerified);

    routeComponent = children;
    pass = true;
    // if the authentication is required and the user is not 
    // authenticated the user is redirected to the sign in page
    if (requireAuth && !isAuthenticated) {
      console.log("Redirecting to signin");
      routePath = "/signin";
      pass=false;
    }  
    // if the email verification is required and the user 
    // is not verified the user is redirected to the confirm email page
    else if (requireEmailVerification && !isEmailVerified) {
      console.log("Redirecting to confirm email");
      routePath = "/confirm-email";
      pass=false;
    }
  }, [isAuthenticated, isEmailVerified]);
  
  
  return (pass ? routeComponent : <Navigate to={routePath} />);
};

RouteProtector.propTypes = {
  children: PropTypes.node.isRequired,
  requireAuth: PropTypes.bool,
  requireEmailVerification: PropTypes.bool,
  redirectTo: PropTypes.string,
};

export default RouteProtector;
