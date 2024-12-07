import React from "react";
import SpaceDashboardTwoToneIcon from "@mui/icons-material/SpaceDashboardTwoTone";

const SCDrawerIcon = ({ color = "customIcon.main" }) => {
  return (
    <SpaceDashboardTwoToneIcon
      sx={{
        color: (theme) => {
          const [paletteKey, shade] = color.split(".");
          return theme.palette[paletteKey]?.[shade] || theme.palette.text.primary;
        },
      }}
    />
  );
};

export default SCDrawerIcon;
