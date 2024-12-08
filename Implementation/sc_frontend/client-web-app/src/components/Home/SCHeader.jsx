import * as React from "react";
import Stack from "@mui/material/Stack";
import NotificationsRoundedIcon from "@mui/icons-material/NotificationsRounded";
import SCNavbarBreadcrumbs from "./SCNavbarBreadcrumbs";
import SCUserOptionsMenu from "./SCUserOptionsMenu";

import SCMenuButton from "./SCMenuButton";
import SCColorModeIconDropdown from "./SCColorModeIconDropdown";

export default function SCHeader() {
  return (
    <Stack
      direction="row"
      sx={{
        display: { xs: "none", md: "flex" },
        width: "100%",
        alignItems: { xs: "flex-start", md: "center" },
        justifyContent: "space-between",
        maxWidth: { sm: "100%", md: "1700px" },
        pt: 1.5,
      }}
      spacing={2}
    >
      <SCNavbarBreadcrumbs />
      <Stack direction="row" sx={{ gap: 1 }}>
        <SCColorModeIconDropdown />
        <SCMenuButton showBadge aria-label="Open notifications">
          <NotificationsRoundedIcon />
        </SCMenuButton>
        <SCUserOptionsMenu />
      </Stack>
    </Stack>
  );
}
