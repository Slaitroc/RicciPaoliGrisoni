import React from "react";
import { Box, CircularProgress, CssBaseline } from "@mui/material";
import SCAppTheme from "../components/Shared/SCAppTheme";

export const LoadingPage = (props) => {
  return (
    <>
      <SCAppTheme {...props}>
        <CssBaseline enableColorScheme />
        <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
          <Box display="flex" justifyContent="center" alignItems="center">
            <CircularProgress size="3rem" />
          </Box>
        </Box>
      </SCAppTheme>
    </>
  );
};
