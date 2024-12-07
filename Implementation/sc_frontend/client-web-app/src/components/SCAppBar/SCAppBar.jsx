import React from "react";
import { styled } from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import IconButton from "@mui/material/IconButton";
import SCMenuIcon from "../SCIcons/SCMenuIcon";

const AppBarStyled = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open" && prop !== "drawerWidth",
})(({ theme, drawerWidth, open }) => ({
  transition: theme.transitions.create(["margin", "width"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  variants: [
    {
      props: ({ open, drawerWidth }) => open,
      style: {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: `${drawerWidth}px`,
        transition: theme.transitions.create(["margin", "width"], {
          easing: theme.transitions.easing.easeOut,
          duration: theme.transitions.duration.enteringScreen,
        }),
      },
    },
  ],
}));

const SCAppBar = ({ children, drawerWidth, open, onLeftIconClick }) => {
  return (
    <AppBarStyled
      open={open}
      drawerWidth={drawerWidth}
      position="static"
      sx={{
        boxShadow: (theme) => theme.shadows[2],
        bgcolor: "background.default",
      }}
    >
      <Container maxWidth="xl">
        <Toolbar
          disableGutters
          sx={{
            bgcolor: "#222831",
          }}
        >
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={onLeftIconClick}
            edge="start"
            sx={[
              {
                mr: 2,
              },
              open && { display: "none" },
            ]}
          >
            <SCMenuIcon></SCMenuIcon>
          </IconButton>
          <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ color: (theme) => theme.palette.text.primary }}
          >
            Student & Companies
          </Typography>
        </Toolbar>
        {children}
      </Container>
    </AppBarStyled>
  );
};
export default SCAppBar;
