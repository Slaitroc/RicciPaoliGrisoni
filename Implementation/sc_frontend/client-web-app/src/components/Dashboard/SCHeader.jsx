import * as React from "react";
import Stack from "@mui/material/Stack";
import NotificationsRoundedIcon from "@mui/icons-material/NotificationsRounded";
import SCNavbarBreadcrumbs from "./SCNavbarBreadcrumbs";
import SCAvatarOptionsMenu from "../Shared/OptionMenu/SCAvatarOptionsMenu";
import NotificationContainer from "../Notification/NotificationContainer";
import SCMenuButton from "./SCMenuButton";
import SCColorModeIconDropdown from "./SCColorModeIconDropdown";

export default function SCHeader() {
  return (
    <Stack
      direction="row"
      sx={{
        display: { xs: "none", md: "flex" },
        alignItems: { xs: "flex-start", md: "center" },
        justifyContent: "space-between",
        maxWidth: { sm: "100%", md: "100%" },
        pt: 1.5,
      }}
      spacing={0}
    >
      <SCNavbarBreadcrumbs />
      <Stack direction="row" sx={{ gap: 1 }}>
        <SCColorModeIconDropdown />
        <NotificationContainer />
        <SCAvatarOptionsMenu />
      </Stack>
    </Stack>
  );
}
