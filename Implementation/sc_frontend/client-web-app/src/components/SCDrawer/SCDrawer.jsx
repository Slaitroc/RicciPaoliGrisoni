import React from "react";
import Divider from "@mui/material/Divider";
import Drawer from "@mui/material/Drawer";
import SCDrawerHeader from "./SCDrawerHeader/SCDrawerHeader";
import SCDrawerList from "./SCDrawerList/SCDrawerList";

const SCDrawer = ({ drawerWidth, open, onDrawerClose }) => {
  return (
    <Drawer
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        "& .MuiDrawer-paper": {
          boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.6)",
          width: drawerWidth,
          boxSizing: "border-box",
          bgcolor: (theme) => theme.palette.background.default,
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
  );
};

export default SCDrawer;
