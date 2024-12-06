import React from "react";
import { styled } from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";




const AppBarStyled = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== "open" && prop !=="drawerWidth",
    })(({ theme, drawerWidth, open }) => ({
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    variants: [
      {
        props: ({ open, drawerWidth}) => open,
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

  const SCAppBar = ({children, drawerWidth, open}) =>{
    return <AppBarStyled open={open} drawerWidth={drawerWidth}>{children}</AppBarStyled>
  }
  export default SCAppBar;
  