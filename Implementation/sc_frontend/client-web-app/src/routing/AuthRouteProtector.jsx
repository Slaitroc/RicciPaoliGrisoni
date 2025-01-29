import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import { useGlobalContext } from "../global/GlobalContext";
import { Navigate } from "react-router-dom";

/**
 * AuthRouteProtector component to protect routes based on authentication status.
 *
 * @param {Object} props - The component props.
 * @param {React.ReactNode} props.children - The child components to render if the condition is met.
 * @param {boolean} props.equals - The expected authentication status to allow access.
 * @param {string} props.navigateTo - The path to navigate to if the condition is not met.
 * @returns {React.ReactNode} - The children if the condition is met, otherwise a <Navigate /> component.
 */
const AuthRouteProtector = ({
  children,
  redirectTo = "/signin"
}) => {
  const { isAuthenticated } = useGlobalContext();
  const [ pass, setPass ] = useState(true);
  
  useEffect(() => {
    console.log("isAuthenticated", isAuthenticated);

    // if the authentication is required and the user is not 
    // authenticated the user is redirected to the sign in page
    if (!isAuthenticated) {
      console.log("Redirecting ...");
      setPass(false);
    }
  }, [isAuthenticated]);
  
  if (!pass) {
    return <Navigate to={redirectTo} />;
  }

  return children;
    
};


export default AuthRouteProtector;
