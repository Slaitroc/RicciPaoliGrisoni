import React from "react";
import Divider from "@mui/material/Divider";
import Drawer from "@mui/material/Drawer";
import SCDrawerHeader from "./SCDrawerHeader/SCDrawerHeader";
import SCDrawerList from "./SCDrawerList/SCDrawerList";

const SCDrawer = ({ drawerWidth, open, onDrawerClose }) => {
  return (
    <Drawer
      sx={{
        flexShrink: 0,
        "& .MuiDrawer-paper": {
          bgcolor: (theme) => theme.palette.background.default,
          boxShadow: (theme) => theme.shadows[2],
          width: drawerWidth,
          boxSizing: "border-box",
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
