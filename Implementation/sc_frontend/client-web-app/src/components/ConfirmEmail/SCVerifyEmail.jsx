import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import { getAuth, applyActionCode } from "firebase/auth";
import { useGlobalContext } from "../../global/GlobalContext";
import * as apiCalls from "../../api-calls/apiCalls";
import CssBaseline from "@mui/material/CssBaseline";
import SCAppTheme from "../Shared/SCAppTheme";

export const SCVerifyEmail = () => {
  const [message, setMessage] = useState("Verifying your email...");
  const auth = getAuth();
  const { setIsEmailVerified, isAuthenticated } = useGlobalContext();

  useEffect(() => {
    if (isAuthenticated === false) {
      setMessage("Wait a moment...");
      setTimeout(() => {
        setMessage("Failed to verify email. Are you logged in?");
      }, 6000);
      return;
    }
    const user = auth.currentUser;
    if (user) {
      user.reload().then(() => {
        if (user.emailVerified) {
          //TODO da sistemare, così riverifica la mail ogni volta...in realtà le volte successive il backend ignora la conferma
          apiCalls.sendEmailConfirmed();
          setIsEmailVerified(true);
          setMessage("Your email has been verified! 🎉");
        } else {
          setMessage("Failed to verify email. The link may have expired.");
        }
      });
    } else {
      setMessage("Invalid verification link.");
    }
  }, [isAuthenticated]);

  return (
    <>
      <SCAppTheme>
        <CssBaseline enableColorScheme />
        <Box
          display="flex"
          justifyContent="center"
          alignItems="center"
          height="100vh"
        >
          <div>{message}</div>
        </Box>
      </SCAppTheme>
    </>
  );
};
