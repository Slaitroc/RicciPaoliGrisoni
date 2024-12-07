import React from "react";
import Divider from "@mui/material/Divider";
import Drawer from "@mui/material/Drawer";
import { useTheme } from "@mui/material";
import SCDrawerHeader from "./SCDrawerHeader/SCDrawerHeader";
import SCDrawerList from "./SCDrawerList/SCDrawerList"


const SCDrawer = ({drawerWidth, open, onDrawerClose}) => {
    const theme = useTheme();
    return (
        <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
            bgcolor: (theme)=>theme.palette.background.paper,
          },
        }}
        variant="persistent"
        anchor="left"
        open={open}
      >
        <SCDrawerHeader onDrawerClose={onDrawerClose}></SCDrawerHeader>
        <Divider />
        <SCDrawerList></SCDrawerList>
        <Divider />
      </Drawer>
    )

}

export default SCDrawer;