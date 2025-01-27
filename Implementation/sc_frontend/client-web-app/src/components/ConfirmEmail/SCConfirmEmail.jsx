import React, { useEffect, useState } from "react";
import { getAuth, applyActionCode } from "firebase/auth";
import { useGlobalContext } from "../../global/GlobalContext";
import * as apiCalls from "../../api-calls/apiCalls";

export const SCConfirmEmail = () => {
  const [message, setMessage] = useState("Verifying your email...");
  const auth = getAuth();
  const { uuid } = useGlobalContext();

  useEffect(() => {
    const queryParams = new URLSearchParams(window.location.search);
    const mode = queryParams.get("mode");
    const oobCode = queryParams.get("oobCode");

    if (mode === "verifyEmail" && oobCode) {
      // Verifica il codice di conferma
      applyActionCode(auth, oobCode)
        .then(() => {
          setMessage("Your email has been verified! 🎉");
        })
        .catch((error) => {
          setMessage("Failed to verify email. The link may have expired.");
        });
    } else {
      // controlla se l'utente ha già verifiacto la mail
      auth.onAuthStateChanged((user) => {
        if (user) {
          user.reload().then(() => {
            if (user.emailVerified) {
              //TODO da sistemare, così riverifica la mail ogni volta...in realtà le volte successive il backend ignora la conferma
              apiCalls.sendEmailConfirmed();
              setMessage("Your email is already verified.");
            } else {
              setMessage("Invalid verification link.");
            }
          });
        } else {
          setMessage("Invalid verification link.");
        }
      });
    }
  }, []);

  return <div>{message}</div>;
};
