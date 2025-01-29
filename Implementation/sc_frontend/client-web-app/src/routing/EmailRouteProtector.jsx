import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { Navigate } from "react-router-dom";
import { useGlobalContext } from "../global/GlobalContext";

/**
 * EmailRouteProtector component to protect routes based on email verification status.
 *
 * @param {Object} props - The component props.
 * @param {React.ReactNode} props.children - The child components to render if the condition is met.
 * @param {boolean} props.requireEmailVerification - The expected email verification status to allow access.
 * @returns {React.ReactNode} - The children if the condition is met, otherwise a <Navigate /> component.
 */
const EmailRouteProtector = ({ 
  children}) => {
  const { isEmailVerified } = useGlobalContext();
  const [pass, setPass] = useState(true);
    
  useEffect(() => {
    console.log("isEmailVerified", isEmailVerified);

    // if the email verification is required and the user 
    // is not verified the user is redirected to the confirm email page
    if (!isEmailVerified) {
      console.log("Redirecting to confirm email");
      setPass(false);
    } else {
      setPass(true);
    }
  }, [isEmailVerified]);

  if (!pass) {
    return <Navigate to="/confirm-email" />;
  }

  return children;
};


export default EmailRouteProtector;