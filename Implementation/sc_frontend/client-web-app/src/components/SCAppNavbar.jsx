import * as React from "react";
import { styled } from "@mui/material/styles";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Stack from "@mui/material/Stack";
import MuiToolbar from "@mui/material/Toolbar";
import { tabsClasses } from "@mui/material/Tabs";
import Typography from "@mui/material/Typography";
import MenuRoundedIcon from "@mui/icons-material/MenuRounded";
import DashboardRoundedIcon from "@mui/icons-material/DashboardRounded";
import SCSideMenuMobile from "./SCSideMenuMobile";
import SCMenuButton from "./SCMenuButton";
import SCColorModeIconDropdown from "./SCColorModeIconDropdown";
import { TEXT } from "../constants/UIConstants";
import NotificationsRoundedIcon from "@mui/icons-material/NotificationsRounded";
import SCUserOptionsMenu from "./SCUserOptionsMenu";

const Toolbar = styled(MuiToolbar)({
  width: "100%",
  padding: "12px",
  display: "flex",
  flexDirection: "column",
  alignItems: "start",
  justifyContent: "center",
  gap: "12px",
  flexShrink: 0,
  [`& ${tabsClasses.flexContainer}`]: {
    gap: "8px",
    p: "8px",
    pb: 0,
  },
});

export default function SCAppNavbar() {
  const [open, setOpen] = React.useState(false);

  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };

  return (
    <AppBar
      position="fixed"
      sx={{
        display: { xs: "auto", md: "none" },
        // display: "flex",
        boxShadow: 0,
        bgcolor: "background.paper",
        backgroundImage: "none",
        borderBottom: "1px solid",
        borderColor: "divider",
        top: "var(--template-frame-height, 0px)",
      }}
    >
      <Toolbar variant="regular">
        <Stack
          direction="row"
          sx={{
            alignItems: "center",
            flexGrow: 1,
            width: "100%",
            gap: 1,
          }}
        >
          <SCMenuButton aria-label="menu" onClick={toggleDrawer(true)}>
            <MenuRoundedIcon />
          </SCMenuButton>
          <SCSideMenuMobile open={open} toggleDrawer={toggleDrawer} />
          <Stack
            direction="row"
            spacing={1}
            sx={{ justifyContent: "center", mr: "auto" }}
          >
            {/* <CustomIcon /> */}
            <Typography
              variant="h4"
              component="h1"
              sx={{ color: "text.primary" }}
            >
              {TEXT.FULL_SIGN}
            </Typography>
          </Stack>
          <SCColorModeIconDropdown />
          <SCMenuButton showBadge={false}>
            <NotificationsRoundedIcon />
          </SCMenuButton>
          <SCUserOptionsMenu />
        </Stack>
      </Toolbar>
    </AppBar>
  );
}

export function CustomIcon() {
  return (
    <Box
      sx={{
        width: "1.5rem",
        height: "1.5rem",
        bgcolor: "black",
        borderRadius: "999px",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        alignSelf: "center",
        backgroundImage:
          "linear-gradient(135deg, hsl(210, 98%, 60%) 0%, hsl(210, 100%, 35%) 100%)",
        color: "hsla(210, 100%, 95%, 0.9)",
        border: "1px solid",
        borderColor: "hsl(210, 100%, 55%)",
        boxShadow: "inset 0 2px 5px rgba(255, 255, 255, 0.3)",
      }}
    >
      <DashboardRoundedIcon color="inherit" sx={{ fontSize: "1rem" }} />
    </Box>
  );
}
