import * as React from "react";
import Stack from "@mui/material/Stack";
import NotificationsRoundedIcon from "@mui/icons-material/NotificationsRounded";
import NavbarBreadcrumbs from "./Templates/dashboard/components/NavbarBreadcrumbs";
import SCUserOptionsMenu from "./SCUserOptionsMenu";

import MenuButton from "./Templates/dashboard/components/MenuButton";
import ColorModeIconDropdown from "./Templates/shared-theme/ColorModeIconDropdown";

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
      <NavbarBreadcrumbs />
      <Stack direction="row" sx={{ gap: 1 }}>
        <ColorModeIconDropdown />
        <MenuButton showBadge aria-label="Open notifications">
          <NotificationsRoundedIcon />
        </MenuButton>
        <SCUserOptionsMenu />
      </Stack>
    </Stack>
  );
}
