import { Box, Button, Chip, FormLabel, TextField } from "@mui/material";
import React from "react";
import * as authorization from "../api-calls/api-wrappers/authorization-wrapper/authorization";
import * as apiCalls from "../api-calls/apiCalls";
import { auth } from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";

export const FirebaseTestPage = () => {
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [token, setToken] = React.useState("");

  const clickRegisterUser = async () => {
    try {
      const response = await authorization.register(email, password);
      switch (response.code) {
        case 400:
          console.log("Bad Request: invalid credentials");
          break;
        case 500:
          console.error(response.err);
          break;
        case 200:
          console.log("User registered. Token:", typeof response.token);
          const showToken =
            typeof response.token === "string"
              ? response.token.substring(0, 10).concat("...")
              : "No Token";
          setToken(showToken);
      }
    } catch (err) {
      throw err;
    }
  };
  const clickLoginUser = async () => {
    try {
      const response = await authorization.login(email, password);

      switch (response.code) {
        case 204:
          console.log("No Content: unknown credentials");
          break;
        case 400:
          console.log("Bad Request: missing credentials");
          break;
        case 401:
          console.log("Unauthorized: no matching token");
          break;
        case 500:
          console.log("Internal Server Error: unexpected response");
          break;
        case 200:
          console.log("User logged in. Token:", response.token);
          if (typeof response.token === "string" && response.token.length > 0) {
            setToken(response.token.substring(0, 10).concat("..."));
          } else if (typeof response.token === "number") {
            setToken(response.token);
          }
      }
    } catch (err) {
      throw err;
    }
  };
  const clickLogoutUser = async () => {
    try {
      const response = await authorization.logout();
      switch (response.code) {
        case 500:
          console.log("Internal Server Error");
        case 200:
          console.log("User logged out successfully!");
          setToken("No Token");
      }
    } catch (err) {
      throw err;
    }
  };
  const clickGetToken = () => {
    const token = auth.currentUser.getIdToken();
    const showToken =
      typeof token === "string"
        ? token.substring(0, 10).concat("...")
        : "No Token";
    setToken(showToken);
  };
  const clickForgetToken = () => {
    tokenStorage.clearToken();
    setToken("No Token");
  };
  const clickTestRequest = () => {
    console.log("Test Request");
    apiCalls
      .testProxy()
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);
      });
  };

  const clickConfirmEmail = async () => {
    await apiCalls.sendEmailConfirmed();
  };



  return (
    // <>
    //   <Box
    //     display="flex"
    //     justifyContent="center"
    //     flexDirection="column"
    //     alignItems="center"
    //   >
    //     {/* INSERT CREDENTIAL & REGISTER */}
    //     <Box
    //       display="flex"
    //       justifyContent="center"
    //       flexDirection="row"
    //       width="40%"
    //     >
    //       <Box margin={3}>
    //         <FormLabel htmlFor="name">Email</FormLabel>
    //         <TextField
    //           autoComplete="Email"
    //           name="email"
    //           required
    //           onChange={(e) => setEmail(e.target.value)}
    //           fullWidth
    //           id="email"
    //           placeholder="Email"
    //         />
    //       </Box>
    //       <Box margin={3}>
    //         <FormLabel htmlFor="name">Password</FormLabel>
    //         <TextField
    //           autoComplete="Password"
    //           name="pass"
    //           required
    //           onChange={(e) => setPassword(e.target.value)}
    //           fullWidth
    //           id="pass"
    //           placeholder="Password"
    //         />
    //       </Box>
    //     </Box>
    //     {/* REGISTER & LOGIN */}
    //     <Box gap={2} display="flex" justifyContent="center" flexDirection="row">
    //       <Button variant="contained" onClick={clickRegisterUser}>
    //         Register
    //       </Button>
    //       <Button variant="contained" onClick={clickLoginUser}>
    //         Login
    //       </Button>
    //       <Button variant="contained" onClick={clickLogoutUser}>
    //         Logout
    //       </Button>
    //     </Box>
    //     <Box
    //       display="flex"
    //       justifyContent="center"
    //       alignItems="center"
    //       flexDirection="row"
    //       width="50%"
    //       margin={5}
    //     >
    //       <Box margin={3} display="flex" gap={2}>
    //         <Chip label={token} />

    //         <Button variant="contained" onClick={clickForgetToken}>
    //           Forget Token
    //         </Button>
    //         <Button variant="contained" onClick={clickGetToken}>
    //           Get Token from Storage
    //         </Button>
    //       </Box>
    //     </Box>
    //     <Box margin={3} display="flex" gap={2}>
    //       <Button variant="contained" onClick={clickTestRequest}>
    //         Test Request
    //       </Button>
    //     </Box>
    //   </Box>
    // </>
    <>
      <Box
        display="flex"
        justifyContent="center"
        flexDirection="column"
        alignItems="center"
      >
        <Box margin={3} display="flex" gap={2}>

          <Button variant="contained" onClick={clickConfirmEmail}>
            Confirm Email
          </Button>
        </Box>
      </Box>
    </>
  );
};
