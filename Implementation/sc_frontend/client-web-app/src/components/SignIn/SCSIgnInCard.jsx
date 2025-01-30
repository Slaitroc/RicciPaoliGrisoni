import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import MuiCard from "@mui/material/Card";
import Checkbox from "@mui/material/Checkbox";
import FormLabel from "@mui/material/FormLabel";
import FormControl from "@mui/material/FormControl";
import FormControlLabel from "@mui/material/FormControlLabel";
import Link from "@mui/material/Link";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import SCForgotPassword from "./SCForgotPassword";
import { styled } from "@mui/material/styles";
import { SitemarkIcon } from "../Templates/sign-in-side/CustomIcons";
import { useGlobalContext } from "../../global/GlobalContext";
import { useNavigate } from "react-router-dom";
import { Alert } from "@mui/material";
import * as account from "../../api-calls/api-wrappers/account-wrapper/account";

import * as authorization from "../../api-calls/api-wrappers/authorization-wrapper/authorization";

const Card = styled(MuiCard)(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  alignSelf: "center",
  width: "100%",
  padding: theme.spacing(4),
  gap: theme.spacing(2),
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

export default function SCSignInCard() {
  //TODO usa getElementByID per prendere i valori dei campi email e password
  // Stati per email e password
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [emailError, setEmailError] = React.useState(false);
  const [emailErrorMessage, setEmailErrorMessage] = React.useState("");
  const [passwordError, setPasswordError] = React.useState(false);
  const [passwordErrorMessage, setPasswordErrorMessage] = React.useState("");
  const [open, setOpen] = React.useState(false);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  const { setIsAuthenticated, setProfile, setUserType } = useGlobalContext();
  const navigate = useNavigate();

  const handlePassResetOpen = () => {
    setOpen(true);
  };

  const handlePassResetClose = () => {
    setOpen(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (validateInputs()) {
      authorization.login(email, password).then((response) => {
        if (response.status === 500) {
          // DEBUG
          console.log("Debugs", response);
          response.json().then((data) => {
            setOpenAlert(true);
            setAlertSeverity("error");
            setAlertMessage(data.properties.error);
          });
        } else if (response.status === 200) {
          response.json().then((data) => {
            //setIsAuthenticated(true);
            setOpenAlert(true);
            setAlertSeverity("success");
            setAlertMessage(data.properties.message);
          });
        }
      });
      // TODO sicuri vada qui?
      Notification.requestPermission().then((permission) => {
        if (permission === "granted") {
          console.log("Permesso per le notifiche concesso.");
        } else {
          console.log("Permesso per le notifiche negato.");
        }
      });
    }
  };

  const validateInputs = () => {
    let isValid = true;

    if (!email || !/\S+@\S+\.\S+/.test(email)) {
      setEmailError(true);
      setEmailErrorMessage("Please enter a valid email address.");
      isValid = false;
      setEmail(""); // Cancella email non valida
    } else {
      setEmailError(false);
      setEmailErrorMessage("");
    }

    if (!password || password.length < 6) {
      setPasswordError(true);
      setPasswordErrorMessage("Password must be at least 6 characters long.");
      isValid = false;
      setPassword(""); // Cancella password non valida
    } else {
      setPasswordError(false);
      setPasswordErrorMessage("");
    }

    return isValid;
  };

  return (
    <Card variant="outlined">
      {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}
      <Box sx={{ display: { xs: "flex", md: "none" } }}>
        <SitemarkIcon />
      </Box>
      <Typography
        component="h1"
        variant="h4"
        sx={{ width: "100%", fontSize: "clamp(2rem, 10vw, 2.15rem)" }}
      >
        Sign in
      </Typography>
      <Box
        component="form"
        onSubmit={handleSubmit}
        noValidate
        sx={{ display: "flex", flexDirection: "column", width: "100%", gap: 2 }}
      >
        <FormControl>
          <FormLabel htmlFor="email">Email</FormLabel>
          <TextField
            error={emailError}
            helperText={emailErrorMessage}
            id="email"
            type="email"
            name="email"
            placeholder="your@email.com"
            autoComplete="email"
            autoFocus
            required
            fullWidth
            variant="outlined"
            color={emailError ? "error" : "primary"}
            value={email} // Collega lo stato all'input
            onChange={(e) => setEmail(e.target.value)} // Aggiorna lo stato
          />
        </FormControl>
        <FormControl>
          <Box sx={{ display: "flex", justifyContent: "space-between" }}>
            <FormLabel htmlFor="password">Password</FormLabel>
          </Box>
          <TextField
            error={passwordError}
            helperText={passwordErrorMessage}
            name="password"
            placeholder="••••••"
            type="password"
            id="password"
            autoComplete="current-password"
            autoFocus
            required
            fullWidth
            variant="outlined"
            color={passwordError ? "error" : "primary"}
            value={password} // Collega lo stato all'input
            onChange={(e) => setPassword(e.target.value)} // Aggiorna lo stato
          />
        </FormControl>
        <Link
          component="button"
          type="button"
          onClick={handlePassResetOpen}
          variant="body2"
          sx={{ alignSelf: "baseline" }}
        >
          Forgot your password?
        </Link>
        <FormControlLabel
          control={<Checkbox value="remember" color="primary" />}
          label="Remember me"
        />

        <SCForgotPassword open={open} handleClose={handlePassResetClose} />
        <Button type="submit" fullWidth variant="contained">
          Sign in
        </Button>
        <Typography sx={{ textAlign: "center" }}>
          Don&apos;t have an account?{" "}
          <span>
            <Link
            //NAV to signup
              onClick={() => {
                navigate("/signup");
              }}
              variant="body2"
              sx={{ alignSelf: "center" }}
            >
              Sign up
            </Link>
          </span>
        </Typography>
      </Box>
    </Card>
  );
}
