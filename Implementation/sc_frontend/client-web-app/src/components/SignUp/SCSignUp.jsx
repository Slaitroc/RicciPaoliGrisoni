import React from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";

import Typography from "@mui/material/Typography";
import MuiCard from "@mui/material/Card";
import {
  Alert,
  Divider,
  FormControl,
  FormLabel,
  TextField,
} from "@mui/material";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import * as authorization from "../../api-calls/api-wrappers/authorization-wrapper/authorization";
import { useGlobalContext } from "../../global/GlobalContext";

const Card = styled(MuiCard)(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  alignSelf: "center",
  width: "100%",
  maxWidth: "450px", // Larghezza massima
  padding: theme.spacing(4),
  gap: theme.spacing(2),
  margin: "auto",
  boxShadow:
    "hsla(220, 30%, 5%, 0.05) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.05) 0px 15px 35px -5px",
  [theme.breakpoints.up("sm")]: {
    width: "450px",
  },
  ...theme.applyStyles("dark", {
    boxShadow:
      "hsla(220, 30%, 5%, 0.5) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.08) 0px 15px 35px -5px",
  }),
}));

export const SCSignUp = () => {
  const navigate = useNavigate();

  const { setIsAuthenticated } = useGlobalContext();

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  const [emailError, setEmailError] = React.useState(false);
  const [emailErrorMessage, setEmailErrorMessage] = React.useState("");
  const [passwordError, setPasswordError] = React.useState(false);
  const [passwordErrorMessage, setPasswordErrorMessage] = React.useState("");

  const handleSubmit = (e) => {
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm-password");
    e.preventDefault();
    if (validateInputs(email, password, confirmPassword)) {
      console.log("Valid credentials");
      authorization.register(email.value, password.value).then((response) => {
        if (response.status === 201) {
          response.json().then((data) => {
            setOpenAlert(true);
            setAlertSeverity("success");
            setAlertMessage(response.json().message);
            setIsAuthenticated(true);
            //NAV to userCreation
            navigate("/signup/user-creation");
          });
        }
        if (response.status === 500) {
          response.json().then((data) => {
            setOpenAlert(true);
            setAlertSeverity("error");
            setAlertMessage(data.properties.error);
          });
        }
        if (response.ok) {
          //NAV to dashboard
          navigate("/dashboard");
        }
      });
    }
  };

  const validateInputs = (email, password, confirmPassword) => {
    let isValid = true;

    if (!email.value || !/\S+@\S+\.\S+/.test(email.value)) {
      setEmailError(true);
      setEmailErrorMessage("Please enter a valid email address.");
      isValid = false;
    } else {
      setEmailError(false);
      setEmailErrorMessage("");
    }

    if (!password.value || password.value.length < 6) {
      setPasswordError(true);
      setPasswordErrorMessage("Password must be at least 6 characters long.");
      isValid = false;
    } else if (password.value !== confirmPassword.value) {
      setPasswordError(true);
      setPasswordErrorMessage("Passwords do not match.");
      isValid = false;
    } else {
      setPasswordError(false);
      setPasswordErrorMessage("");
    }

    return isValid;
  };

  return (
    <>
      <Card variant="outlined">
        <Typography
          component="h1"
          variant="h4"
          align="center"
          sx={{ width: "100%", fontSize: "clamp(2rem, 10vw, 2.15rem)" }}
        >
          Sign Up
        </Typography>
        <Box
          component="form"
          onSubmit={handleSubmit}
          sx={{ display: "flex", flexDirection: "column", gap: 2 }}
        >
          {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}
          <Box justifyContent="center" display="flex"></Box>
          <FormControl>
            <FormLabel htmlFor="email">Email</FormLabel>
            <TextField
              id="email"
              placeholder="your@email.com"
              name="email"
              autoComplete="email"
              variant="outlined"
              required
              fullWidth
              error={emailError}
              helperText={emailErrorMessage}
              color={passwordError ? "error" : "primary"}
            />
          </FormControl>
          <FormControl>
            <FormLabel htmlFor="password">Password</FormLabel>
            <TextField
              id="password"
              name="password"
              placeholder="••••••"
              type="password"
              autoComplete="new-password"
              variant="outlined"
              required
              fullWidth
              error={passwordError}
              helperText={passwordErrorMessage}
              color={passwordError ? "error" : "primary"}
            />
          </FormControl>
          <FormControl>
            <FormLabel htmlFor="confirm-password">Confirm Password</FormLabel>
            <TextField
              id="confirm-password"
              name="confirm-password"
              placeholder="••••••"
              type="password"
              autoComplete="new-password"
              variant="outlined"
              required
              fullWidth
              error={passwordError}
              helperText={passwordErrorMessage}
              color={passwordError ? "error" : "primary"}
            />
          </FormControl>
          <Button type="submit" fullWidth variant="contained">
            Sign Up
          </Button>
        </Box>
        <Divider>
          <Typography sx={{ color: "text.secondary" }}>or</Typography>
        </Divider>
        <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
          <Typography sx={{ textAlign: "center" }}>
            Already have an account?{" "}
            <Link
              onClick={() => {
                navigate("/signin");
              }}
              variant="body2"
              sx={{ alignSelf: "center" }}
            >
              Sign in
            </Link>
          </Typography>
        </Box>
      </Card>
    </>
  );
};
