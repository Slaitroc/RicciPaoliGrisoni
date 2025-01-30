import * as React from "react";

import { alpha } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Stack from "@mui/material/Stack";
import SCAppNavbar from "./SCAppNavbar";
import MainGrid from "../Templates/dashboard/components/MainGrid";
import SCSideMenu from "./SCSideMenu";
import AppTheme from "../Templates/shared-theme/AppTheme";
import {
  chartsCustomizations,
  dataGridCustomizations,
  datePickersCustomizations,
  treeViewCustomizations,
} from "../Templates/dashboard/theme/customizations";
import SCHeader from "./SCHeader";
import { Outlet } from "react-router-dom";

const xThemeComponents = {
  ...chartsCustomizations,
  ...dataGridCustomizations,
  ...datePickersCustomizations,
  ...treeViewCustomizations,
};

const SCDashboard = (props) => {
  return (
    <AppTheme {...props} themeComponents={xThemeComponents}>
      <CssBaseline enableColorScheme />
      <Box sx={{ display: "flex" }}>
        <SCSideMenu />
        <SCAppNavbar />
        {/* Main content */}
        <Box
          component="main"
          sx={(theme) => ({
            flexGrow: 1,
            backgroundColor: theme.vars
              ? `rgba(${theme.vars.palette.background.defaultChannel} / 1)`
              : alpha(theme.palette.background.default, 1),
          })}
        >
          <Box display="flex" flexDirection="column" paddingTop={2} paddingBottom={1} paddingRight={4} paddingLeft={4}>
            <Box>
              <SCHeader />
            </Box>
            <Box paddingLeft={0} paddingTop={3}>
              <Box height={{xs: "30px", sm:"30px", md: "0px"}}></Box>
              <Outlet />
            </Box>
          </Box>
        </Box>
      </Box>
    </AppTheme>
  );
};

export default SCDashboard;
