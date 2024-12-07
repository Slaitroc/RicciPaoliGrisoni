import * as React from "react";
import { useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import CssBaseline from "@mui/material/CssBaseline";
import Typography from "@mui/material/Typography";
import SCMain from "../SCMain/SCMain"
import SCDrawerHeader from "../SCDrawer/SCDrawerHeader/SCDrawerHeader"
import SCAppBar from "../SCAppBar/SCAppBar"
import SCDrawer from "../SCDrawer/SCDrawer";



const drawerWidth = 240;

export default function SCPage() {
  const theme = useTheme();
  const [open, setOpen] = React.useState(false);
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <SCAppBar position="fixed" open={open} drawerWidth={drawerWidth} onLeftIconClick={handleDrawerOpen}></SCAppBar>
      <SCDrawer open={open} drawerWidth={drawerWidth} onDrawerClose={handleDrawerClose}></SCDrawer>
      <SCMain open={open} drawerWidth={drawerWidth}></SCMain>
    </Box>
  );
}
