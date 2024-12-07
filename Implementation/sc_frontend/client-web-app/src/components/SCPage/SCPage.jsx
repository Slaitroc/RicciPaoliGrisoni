import * as React from "react";
import { useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import CssBaseline from "@mui/material/CssBaseline";
import Typography from "@mui/material/Typography";
import SCMain from "../SCMain/SCMain";
import SCDrawerHeader from "../SCDrawer/SCDrawerHeader/SCDrawerHeader";
import SCAppBar from "../SCAppBar/SCAppBar";
import SCDrawer from "../SCDrawer/SCDrawer";
import SCLoggedAppBar from "../SCAppBar/SCLoggedAppBar/SCLoggedAppBar";

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
    <Box
      sx={{
        display: "flex",
        flexDirection: "column", // Organizza il layout verticalmente
        width: "100%", // Evita `vw` per problemi con lo scroll orizzontale
        height: "100vh", // Assicura che il layout copra la pagina intera
        overflowX: "hidden", // Blocca lo scroll orizzontale
      }}
    >
      <CssBaseline />
      {/* <SCAppBar
        position="fixed"
        open={open}
        drawerWidth={drawerWidth}
        onLeftIconClick={handleDrawerOpen}
      ></SCAppBar> */}
      <SCLoggedAppBar
        open={open}
        drawerWidth={drawerWidth}
        onLeftIconClick={handleDrawerOpen}
      ></SCLoggedAppBar>
      <SCDrawer
        open={open}
        drawerWidth={drawerWidth}
        onDrawerClose={handleDrawerClose}
      ></SCDrawer>
      <SCMain open={open} drawerWidth={drawerWidth}>
        ciao
      </SCMain>
    </Box>
  );
}
