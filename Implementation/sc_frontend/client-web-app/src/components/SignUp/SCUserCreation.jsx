import * as React from "react";
import * as globalStatesInit from "../../global/globalStatesInit";
import * as account from "../../api-calls/api-wrappers/account-wrapper/account";
import * as authorization from "../../api-calls/api-wrappers/authorization-wrapper/authorization";
import { styled } from "@mui/material/styles";
import { useGlobalContext } from "../../global/GlobalContext";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { FormLabel } from "@mui/material";
import { SCCountriesSel } from "../Shared/SCCountriesSel";
import { Alert } from "@mui/material";
import { auth } from "../../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";
import { useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";
import FormControl from "@mui/material/FormControl";
import Link from "@mui/material/Link";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import MuiCard from "@mui/material/Card";
import SCSelectLogin from "../Shared/SCSelectLogin";
import Autocomplete from "@mui/material/Autocomplete";

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

//TODO implementare logica per la verifica delle password
export const SCUserCreation = () => {
  const navigate = useNavigate();

  const { setIsAuthenticated } = useGlobalContext();

  const [userType, setUserType] = React.useState(null);

  const [country, setCountry] = React.useState(null);
  const [birthDate, setBirthDate] = React.useState(null);
  const [enrolledInUniVat, setEnrolledInUniVat] = React.useState(null);
  const [location, setLocation] = React.useState(null);
  const [vatNumber, setVatNumber] = React.useState(null);

  const [nameError, setNameError] = React.useState(false);
  const [nameErrorMessage, setNameErrorMessage] = React.useState(null);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  const [fetchedData, setFetchedData] = React.useState([]);

  React.useEffect(() => {
    account
      .getUniversities()
      .then((result) => {
        setFetchedData(result);
        // DEBUG
        //console.log(result.table);
      })
      .catch((error) => {
        console.error("Error during universities retrieval:", error.message);
      });
  }, []);

  React.useEffect(() => {
    account.getUserData().then((response) => {
      //TODO altro?
      if (response.ok) navigate("/dashboard");
    });
  }, []);

  //DEBUG
  React.useEffect(() => {
    //console.log("User type:", userType);
    const userData = {
      userType: userType,
      email: auth.currentUser?.email ? auth.currentUser.email : "",
      name: document.getElementById("name")?.value
        ? document.getElementById("name").value
        : "",
      surname: document.getElementById("surname")?.value
        ? document.getElementById("surname").value
        : "",
      enrolledInUniVat: enrolledInUniVat,
      uniDesc: document.getElementById("description")?.value
        ? document.getElementById("description").value
        : "",
      country: country,
      birthDate: birthDate,
      location: document.getElementById("location")?.value
        ? document.getElementById("location").value
        : "",
      vatNumber: document.getElementById("VAT")?.value
        ? document.getElementById("VAT").value
        : "",
    };
    console.log(userData);
  });

  const validateInputs = (userData) => {
    let isValid = true;
    // TODO controllo dati?? avviene già nel backend per ora sorvolo
    return isValid;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const userData = {
      userType: userType,
      email: auth ? auth.currentUser.email : null,
      name:
        document.getElementById("name")?.value &&
        document.getElementById("name").value != ""
          ? document.getElementById("name").value
          : null,
      surname:
        document.getElementById("surname")?.value &&
        document.getElementById("surname").value != ""
          ? document.getElementById("surname").value
          : null,
      enrolledInUniVat: enrolledInUniVat ? enrolledInUniVat : null,
      uniDesc:
        document.getElementById("description")?.value &&
        document.getElementById("description").value != ""
          ? document.getElementById("description").value
          : null,
      country: country ? country : null,
      birthDate: birthDate ? birthDate : null,
      location:
        document.getElementById("location")?.value &&
        document.getElementById("location").value != ""
          ? document.getElementById("location").value
          : null,
      vatNumber:
        document.getElementById("VAT")?.value &&
        document.getElementById("VAT").value != ""
          ? document.getElementById("VAT").value
          : null,
    };
    // DEBUG
    console.log(userData);
    if (validateInputs(userData)) {
      account.sendUserData(userData).then((response) => {
        if (response.status === 400) {
          response.json().then((data) => {
            setOpenAlert(true);
            setAlertSeverity("error");
            setAlertMessage(data.properties.error);
          });
        } else if (response.status === 500) {
          response.json().then((data) => {
            setOpenAlert(true);
            setAlertSeverity("error");
            setAlertMessage(data.properties.error);
          });
        } else if (response.ok) {
          response.json().then((data) => {
            setOpenAlert(true);
            setAlertSeverity("success");
            setAlertMessage("User created successfully");
            navigate("/dashboard");
          });
        }
      });
    }
  };

  const formType = () => {
    if (userType == globalStatesInit.STUDENT_USER_TYPE)
      return (
        <>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Box display="flex" justifyContent="center" gap={3}>
              <FormControl>
                <FormLabel htmlFor="name">Name</FormLabel>
                <TextField
                  id="name"
                  autoComplete="name"
                  name="name"
                  required
                  fullWidth
                  placeholder="Name"
                  error={nameError}
                  helperText={nameErrorMessage}
                  color={nameError ? "error" : "primary"}
                />
              </FormControl>
              <FormControl>
                <FormLabel htmlFor="surname">Surname</FormLabel>
                <TextField
                  id="surname"
                  autoComplete="surname"
                  name="surname"
                  required
                  fullWidth
                  placeholder="Surname"
                  error={nameError}
                  helperText={nameErrorMessage}
                  color={nameError ? "error" : "primary"}
                />
              </FormControl>
            </Box>
            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="birthdate">Date of Birth</FormLabel>
                <DatePicker
                  onChange={(date) =>
                    //DANGER date offset of one day --> day 9 became 8
                    //viene inviata anche nel caso di company e university ma il backend la ignora
                    //NOTE ignoring for now
                    setBirthDate(new Date(date).toISOString())
                  }
                  views={["year", "month", "day"]}
                  sx={{
                    "& .MuiIconButton-root": {
                      width: "30px",
                      height: "30px",
                      padding: "4px",
                      border: "none",
                    },
                  }}
                />
              </FormControl>
            </Box>
            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="country">Select Country</FormLabel>
                <SCCountriesSel
                  onChange={(e, value) => {
                    value && setCountry(value.code);
                  }}
                />
              </FormControl>
            </Box>

            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="universities">Select University</FormLabel>
                <Autocomplete
                  id="universities"
                  disablePortal
                  options={fetchedData.names ? fetchedData.names : []}
                  onChange={(e, value) => {
                    if (fetchedData?.table.value === null)
                      value && setEnrolledInUniVat(fetchedData.table[value]);
                  }}
                  sx={{
                    width: 220,
                    "& .MuiAutocomplete-endAdornment .MuiIconButton-root": {
                      width: "30px",
                      height: "30px",
                      padding: "4px",
                      border: "none",
                    },
                  }}
                  renderInput={(params) => <TextField {...params} label="" />}
                />
              </FormControl>
            </Box>
          </LocalizationProvider>
        </>
      );
    else if (userType == globalStatesInit.COMPANY_USER_TYPE)
      return (
        <>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <FormControl>
              <FormLabel htmlFor="name">Company Name</FormLabel>
              <TextField
                id="name"
                autoComplete="name"
                name="name"
                required
                fullWidth
                placeholder="Name"
                error={nameError}
                helperText={nameErrorMessage}
                color={nameError ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="location">Headquarter Location</FormLabel>
              <TextField
                id="location"
                autoComplete="location"
                name="location"
                required
                fullWidth
                // onChange={(e) => setLocation(e.target.value)}
                placeholder="Headquarter location"
                error={nameError}
                helperText={nameErrorMessage}
                color={nameError ? "error" : "primary"}
              />
            </FormControl>
            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="country">Select Country</FormLabel>
                <SCCountriesSel
                  onChange={(e, value) => {
                    setCountry(value.code);
                  }}
                />
              </FormControl>
            </Box>
            <FormControl>
              <FormLabel htmlFor="VAT">VAT number</FormLabel>
              <TextField
                required
                fullWidth
                id="VAT"
                placeholder="VAT"
                name="VAT"
                autoComplete="VAT"
                onChange={(e) => setVatNumber(e.target.value)}
                variant="outlined"
                // error={emailError}
                // helperText={emailErrorMessage}
                // color={passwordError ? "error" : "primary"}
              />
            </FormControl>
          </LocalizationProvider>
        </>
      );
    else if (userType == globalStatesInit.UNIVERSITY_USER_TYPE)
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
              <FormLabel htmlFor="description">
                University Description
              </FormLabel>
              <TextField
                multiline
                id="description"
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
            <Box display="flex" justifyContent="center">
              <FormControl>
                <FormLabel htmlFor="country">Select Country</FormLabel>
                <SCCountriesSel
                  onChange={(e, value) => {
                    value && setCountry(value.code);
                  }}
                />
              </FormControl>
            </Box>
            <FormControl>
              <FormLabel htmlFor="location">Location</FormLabel>
              <TextField
                multiline
                required
                id="location"
                variant="outlined"
                placeholder="Location"
                sx={{
                  "& .MuiOutlinedInput-root": {
                    minHeight: "auto", // Altezza minima dinamica
                    height: "auto", // Altezza complessiva non fissa
                  },
                }}
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
                type="number"
                // error={emailError}
                // helperText={emailErrorMessage}
                // color={passwordError ? "error" : "primary"}
              />
            </FormControl>
          </LocalizationProvider>
        </>
      );
  };

  return (
    <Card variant="outlined">
      {openAlert && <Alert severity={alertSeverity}>{alertMessage}</Alert>}
      <Typography
        component="h1"
        variant="h4"
        align="center"
        sx={{ width: "100%", fontSize: "clamp(2rem, 10vw, 2.15rem)" }}
      >
        User Creation
      </Typography>
      <Box
        component="form"
        onSubmit={handleSubmit}
        sx={{ display: "flex", flexDirection: "column", gap: 2 }}
      >
        <Box justifyContent="center" display="flex">
          <SCSelectLogin setUserType={setUserType} />
        </Box>
        {formType()}
        <Button type="submit" fullWidth variant="contained">
          Send User Information
        </Button>
      </Box>
    </Card>
  );
};
