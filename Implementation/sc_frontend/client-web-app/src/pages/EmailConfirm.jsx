import { Box, Button, Chip, FormLabel, TextField } from "@mui/material";
import React from "react";
import * as apiCalls from "../api-calls/apiCalls";
import * as firebaseAuth from "firebase/auth";
import { auth } from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";

export const EmailConfirm = () => {
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [token, setToken] = React.useState("");

  const clickConfirmEmail = async () => {
    apiCalls
      .sendEmailConfirmed()
      .then((response) => {
        if (response.status === 201) {
          console.log("Email confirmed ---> User unlocked");
        } else if (!response.ok) {
          console.log("Email not confirmed");
          response.json().then((data) => {
            //TODO alert???
            console.log(data.properties.error);
          });
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const resendEmail = async () => {
    try {
      await firebaseAuth.sendEmailVerification(auth.currentUser);
      console.log("Email sent again");
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <Box
        display="flex"
        justifyContent="center"
        flexDirection="column"
        alignItems="center"
        gap={5}
      >
        <Box margin={3} display="flex" gap={2}>
          <Button variant="contained" onClick={clickConfirmEmail}>
            Hack Email (manual confirmation)
          </Button>
        </Box>
        <Box margin={3} display="flex" gap={2}>
          <Button variant="contained" onClick={resendEmail}>
            Resend Email
          </Button>
        </Box>
      </Box>
    </>
  );
};
