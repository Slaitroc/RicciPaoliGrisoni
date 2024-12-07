import React from "react";
import { styled } from "@mui/material/styles";
import Typography from "@mui/material/Typography";

const StyledMain = styled("main", {
  shouldForwardProp: (prop) => prop !== "open" && prop !== "drawerWidth",
})(({ theme, open, drawerWidth }) => ({
  flexGrow: 1,
  minHeight: "100vh", // Per coprire l'intero viewport in altezza
  padding: theme.spacing(3),
  transition: theme.transitions.create("margin", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  marginLeft: -`${drawerWidth}px`,
  ...(open && {
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen - 40,
    }),
    marginLeft: `${drawerWidth}px`,
  }),
}));

const SCMain = ({ open, drawerWidth, children }) => {
  return (
    <StyledMain open={open} drawerWidth={drawerWidth}>
      {!open ? (
        <Typography sx={{ marginBottom: 2 }}>MenuClosed</Typography>
      ) : (
        <Typography sx={{ marginBottom: 2 }}>MenuOpen</Typography>
      )}
      {children}
    </StyledMain>
  );
};

export default SCMain;
