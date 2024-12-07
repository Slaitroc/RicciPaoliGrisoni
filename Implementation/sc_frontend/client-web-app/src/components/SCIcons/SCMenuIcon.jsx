import React from "react";
import MenuIcon from "@mui/icons-material/Menu";

const SCMenuIcon = ({ color = "customIcon.main" }) => {
  return (
    <MenuIcon
      sx={{
        color: (theme) => {
          const [paletteKey, shade] = color.split(".");
          return (
            theme.palette[paletteKey]?.[shade] || theme.palette.text.primary
          );
        },
      }}
    />
  );
};

export default SCMenuIcon;
