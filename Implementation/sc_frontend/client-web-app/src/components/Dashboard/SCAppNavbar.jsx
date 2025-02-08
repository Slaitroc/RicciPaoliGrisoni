import * as React from "react";
import { styled, useTheme } from "@mui/material/styles";
import AppBar from "@mui/material/AppBar";
import Stack from "@mui/material/Stack";
import MuiToolbar from "@mui/material/Toolbar";
import { tabsClasses } from "@mui/material/Tabs";
import Typography from "@mui/material/Typography";
import SCSideMenuMobile from "./SCSideMenuMobile";
import SCMenuButton from "./SCMenuButton";
import SCColorModeIconDropdown from "./SCColorModeIconDropdown";
import { TEXT } from "../../constants/UIConstants";
import SCAvatarOptionsMenu from "../Shared/OptionMenu/SCAvatarOptionsMenu";
import * as SCIcons from "../Shared/SCIcons";
import { useMediaQuery } from "@mui/material";

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

  const theme = useTheme();
  const isSmallScreen = useMediaQuery(theme.breakpoints.down("sm"));

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
            <SCIcons.SCMenuRoundedIcon />
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
              {isSmallScreen ? TEXT.LOGO_NAME : TEXT.FULL_SIGN}
            </Typography>
          </Stack>
          <SCColorModeIconDropdown />
          <SCMenuButton showBadge={false}>
            <SCIcons.SCNotificationsRoundedIcon />
          </SCMenuButton>
          <SCAvatarOptionsMenu />
        </Stack>
      </Toolbar>
    </AppBar>
  );
}
