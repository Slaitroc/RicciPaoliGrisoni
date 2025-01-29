import React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Stack from "@mui/material/Stack";
import SCAppTheme from "../components/Shared/SCAppTheme";
import SCColorModeSelect from "../components/Shared/SCColorModeSelect";
import { Box, styled } from "@mui/material";
import { Outlet } from "react-router-dom";
import SCBackHomeButton from "../components/Dashboard/SCBackHomeButton";

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

export const SignUp = (props) => {
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Submit");
  };

  return (
    <SCAppTheme {...props}>
      <CssBaseline enableColorScheme />
      <SignUpContainer direction="column" justifyContent="space-between">
        <Box display="flex" flexDirection="row" justifyContent="center" gap="50vW" padding={3}>
        <SCBackHomeButton/>
        <SCColorModeSelect />
      </Box>
        <Outlet />
      </SignUpContainer>
    </SCAppTheme>
  );
};
