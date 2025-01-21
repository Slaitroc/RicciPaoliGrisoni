import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Checkbox from "@mui/material/Checkbox";
import CssBaseline from "@mui/material/CssBaseline";
import Divider from "@mui/material/Divider";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormLabel from "@mui/material/FormLabel";
import FormControl from "@mui/material/FormControl";
import Link from "@mui/material/Link";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import MuiCard from "@mui/material/Card";
import SCAppTheme from "../components/Shared/SCAppTheme";
import { styled } from "@mui/material/styles";
import SCColorModeSelect from "../components/Shared/SCColorModeSelect";
import SCBackHomeButton from "../components/Dashboard/SCBackHomeButton";
import { useNavigate } from "react-router-dom";
import SCSelectLogin from "../components/Shared/SCSelectLogin";
import { useGlobalContext } from "../global/globalContext";
import * as global from "../global/globalStatesInit";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import Autocomplete from "@mui/material/Autocomplete";
import { Input } from "@mui/material";
import * as firebaseMethods from "../firebaseMethods";

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

const SignUpContainer = styled(Stack)(({ theme }) => ({
  minHeight: "100vh", // Altezza minima dell'intero viewport
  display: "flex",
  flexDirection: "column",
  padding: theme.spacing(2),
  overflowY: "auto", // Scorrimento verticale
  position: "relative", // Per il background e altri stili
  [theme.breakpoints.up("sm")]: {
    padding: theme.spacing(4),
  },
  "&::before": {
    content: '""',
    display: "block",
    position: "absolute",
    zIndex: -1,
    inset: 0,
    backgroundImage:
      "radial-gradient(ellipse at 50% 50%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))",
    backgroundRepeat: "no-repeat",
    ...theme.applyStyles("dark", {
      backgroundImage:
        "radial-gradient(at 50% 50%, hsla(210, 100%, 16%, 0.5), hsl(220, 30%, 5%))",
    }),
  },
}));

export default function SCSignUp(props) {
  const navigate = useNavigate();
  //TODO implementare logica per la verifica delle password
  const { userType } = useGlobalContext();
  const [emailError, setEmailError] = React.useState(false);
  const [emailErrorMessage, setEmailErrorMessage] = React.useState("");
  const [passwordError, setPasswordError] = React.useState(false);
  const [passwordErrorMessage, setPasswordErrorMessage] = React.useState("");
  const [nameError, setNameError] = React.useState(false);
  const [nameErrorMessage, setNameErrorMessage] = React.useState("");

  const validateInputs = () => {
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const name = document.getElementById("name");

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
    } else {
      setPasswordError(false);
      setPasswordErrorMessage("");
    }

    if (!name.value || name.value.length < 1) {
      setNameError(true);
      setNameErrorMessage("Name is required.");
      isValid = false;
    } else {
      setNameError(false);
      setNameErrorMessage("");
    }

    if (isValid) {
      console.log("Registration successful");
      register(email.value, password.value);
    }

    return isValid;
  };

  const register = async (email, password) => {
    try {
      const token = await firebaseMethods.registerUser(email, password);
      firebaseMethods.saveToken(token);
      navigate("/dashboard");
    } catch (error) {
      console.error("Error during registration:", error.message);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (validateInputs()) {
      const { email, password } = formData;
      register(email, password);
    }
  };

  const formType = () => {
    console.log(userType);
    if (userType == global.student)
      return (
        <>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Box display="flex" justifyContent="center" gap={3}>
              <FormControl>
                <FormLabel htmlFor="name">Name</FormLabel>
                <TextField
                  autoComplete="name"
                  name="name"
                  required
                  fullWidth
                  id="name"
                  placeholder="Name"
                  error={nameError}
                  helperText={nameErrorMessage}
                  color={nameError ? "error" : "primary"}
                />
              </FormControl>
              <FormControl>
                <FormLabel htmlFor="name">Surname</FormLabel>
                <TextField
                  autoComplete="name"
                  name="name"
                  required
                  fullWidth
                  id="surname"
                  placeholder="Surname"
                  error={nameError}
                  helperText={nameErrorMessage}
                  color={nameError ? "error" : "primary"}
                />
              </FormControl>
            </Box>
            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="name">Date of Birth</FormLabel>
                <DatePicker
                  views={["year", "month", "day"]}
                  sx={{
                    "& .MuiIconButton-root": {
                      width: "30px", // Modifica la larghezza del pulsante
                      height: "30px", // Modifica l'altezza del pulsante
                      padding: "4px", // Diminuisce lo spazio interno
                      border: "none",
                    },
                  }}
                />
              </FormControl>
            </Box>
            <FormControl>
              <FormLabel htmlFor="email">Email</FormLabel>
              <TextField
                required
                fullWidth
                id="email"
                placeholder="your@email.com"
                name="email"
                autoComplete="email"
                variant="outlined"
                error={emailError}
                helperText={emailErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="email">Personal Email</FormLabel>
              <TextField
                fullWidth
                id="email"
                placeholder="your@personalemail.com"
                name="email"
                autoComplete="email"
                variant="outlined"
                error={emailError}
                helperText={emailErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="universities">University</FormLabel>
                <Autocomplete
                  disablePortal
                  options={global.universities}
                  sx={{
                    width: 220,
                    "& .MuiAutocomplete-endAdornment .MuiIconButton-root": {
                      width: "30px", // Riduce larghezza del contenitore
                      height: "30px", // Riduce altezza del contenitore
                      padding: "4px", // Opzionale: diminuisce lo spazio interno
                      border: "none",
                    },
                  }}
                  renderInput={(params) => <TextField {...params} label="" />}
                />
              </FormControl>
            </Box>
            <FormControl>
              <FormLabel htmlFor="password">Password</FormLabel>
              <TextField
                required
                fullWidth
                name="password"
                placeholder="••••••"
                type="password"
                id="password"
                autoComplete="new-password"
                variant="outlined"
                error={passwordError}
                helperText={passwordErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              {/* TODO implementare la logica per la verifica tra le due password */}
              <FormLabel htmlFor="password">Confirm Password</FormLabel>
              <TextField
                required
                fullWidth
                name="password"
                placeholder="••••••"
                type="password"
                id="password"
                autoComplete="new-password"
                variant="outlined"
                error={passwordError}
                helperText={passwordErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            {/* <FormControlLabel
              control={<Checkbox value="allowExtraEmails" color="primary" />}
              label="I want to receive updates via email."
            /> */}
          </LocalizationProvider>
        </>
      );
    else if (userType == global.company)
      return (
        <>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <FormControl>
              <FormLabel htmlFor="name">Company Name</FormLabel>
              <TextField
                autoComplete="name"
                name="name"
                required
                fullWidth
                id="name"
                placeholder="Name"
                error={nameError}
                helperText={nameErrorMessage}
                color={nameError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="name">Headquarter</FormLabel>
              <TextField
                autoComplete="name"
                name="name"
                required
                fullWidth
                id="name"
                placeholder="Headquarter location"
                error={nameError}
                helperText={nameErrorMessage}
                color={nameError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="email">Email</FormLabel>
              <TextField
                required
                fullWidth
                id="email"
                placeholder="your@email.com"
                name="email"
                autoComplete="email"
                variant="outlined"
                error={emailError}
                helperText={emailErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="email">VAT number</FormLabel>
              <TextField
                required
                fullWidth
                id="VAT"
                placeholder="VAT"
                name="VAT"
                autoComplete="VAT"
                variant="outlined"
                error={emailError}
                helperText={emailErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="password">Password</FormLabel>
              <TextField
                required
                fullWidth
                name="password"
                placeholder="••••••"
                type="password"
                id="password"
                autoComplete="new-password"
                variant="outlined"
                error={passwordError}
                helperText={passwordErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              {/* TODO implementare la logica per la verifica tra le due password */}
              <FormLabel htmlFor="password">Confirm Password</FormLabel>
              <TextField
                required
                fullWidth
                name="password"
                placeholder="••••••"
                type="password"
                id="password"
                autoComplete="new-password"
                variant="outlined"
                error={passwordError}
                helperText={passwordErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            {/* <FormControlLabel
              control={<Checkbox value="allowExtraEmails" color="primary" />}
              label="I want to receive updates via email."
            /> */}
          </LocalizationProvider>
        </>
      );
    else if (userType == global.university)
      return (
        <>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <FormControl>
              <FormLabel htmlFor="name">University Name</FormLabel>
              <TextField
                autoComplete="name"
                name="name"
                required
                fullWidth
                id="name"
                placeholder="Name"
                error={nameError}
                helperText={nameErrorMessage}
                color={nameError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="name">University Description</FormLabel>
              <TextField
                multiline
                variant="outlined"
                placeholder="Description"
                sx={{
                  "& .MuiOutlinedInput-root": {
                    minHeight: "auto", // Altezza minima dinamica
                    height: "auto", // Altezza complessiva non fissa
                  },
                }}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="name">Internship Office Name</FormLabel>
              <TextField
                autoComplete="name"
                name="name"
                required
                fullWidth
                id="name"
                placeholder="Internship Office Name"
                error={nameError}
                helperText={nameErrorMessage}
                color={nameError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="email">Email</FormLabel>
              <TextField
                required
                fullWidth
                id="email"
                placeholder="your@email.com"
                name="email"
                autoComplete="email"
                variant="outlined"
                error={emailError}
                helperText={emailErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="email">VAT number</FormLabel>
              <TextField
                required
                fullWidth
                id="VAT"
                placeholder="VAT"
                name="VAT"
                autoComplete="VAT"
                variant="outlined"
                error={emailError}
                helperText={emailErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="password">Password</FormLabel>
              <TextField
                required
                fullWidth
                name="password"
                placeholder="••••••••"
                type="password"
                id="password"
                autoComplete="new-password"
                variant="outlined"
                error={passwordError}
                helperText={passwordErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              {/* TODO implementare la logica per la verifica tra le due password */}
              <FormLabel htmlFor="password">Confirm Password</FormLabel>
              <TextField
                required
                fullWidth
                name="password"
                placeholder="••••••••"
                type="password"
                id="password"
                autoComplete="new-password"
                variant="outlined"
                error={passwordError}
                helperText={passwordErrorMessage}
                color={passwordError ? "error" : "primary"}
              />
            </FormControl>
            {/* <FormControlLabel
              control={<Checkbox value="allowExtraEmails" color="primary" />}
              label="I want to receive updates via email."
            /> */}
          </LocalizationProvider>
        </>
      );
  };

  return (
    <SCAppTheme {...props}>
      <CssBaseline enableColorScheme />
      <SCColorModeSelect
        sx={{ position: "fixed", top: "1rem", right: "1rem" }}
      />
      <SignUpContainer direction="column" justifyContent="space-between">
        <SCBackHomeButton></SCBackHomeButton>
        <Card variant="outlined">
          <Typography
            component="h1"
            variant="h4"
            sx={{ width: "100%", fontSize: "clamp(2rem, 10vw, 2.15rem)" }}
          >
            Sign up
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{ display: "flex", flexDirection: "column", gap: 2 }}
          >
            <Box justifyContent="center" display="flex">
              <SCSelectLogin />
            </Box>
            {formType()}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              onClick={validateInputs}
            >
              Sign up
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
      </SignUpContainer>
    </SCAppTheme>
  );
}
