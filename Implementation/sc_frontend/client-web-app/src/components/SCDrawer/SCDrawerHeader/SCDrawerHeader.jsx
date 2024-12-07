import { styled, useTheme } from "@mui/material";
import React from "react";
import IconButton from "@mui/material/IconButton";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import Typography from "@mui/material/Typography";

const DrawerHeaderStyled = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-start",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

const SCDrawerHeader = ({ onDrawerClose }) => {
  const theme = useTheme();
  return (
    <DrawerHeaderStyled>
      <IconButton onClick={onDrawerClose}>
        {theme.direction === "ltr" ? (
          <>
            <ChevronLeftIcon color="primary" />
            <Typography
              variant="h4"
              noWrap
              component="div"
              sx={{ color: (theme) => theme.palette.text.primary }}
            >
              Drawer
            </Typography>
          </>
        ) : (
          <ChevronRightIcon />
        )}
      </IconButton>
    </DrawerHeaderStyled>
  );
};

export default SCDrawerHeader;
