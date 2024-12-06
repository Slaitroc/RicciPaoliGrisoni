import * as React from "react";
import { styled, useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import CssBaseline from "@mui/material/CssBaseline";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import BusinessCenterTwoToneIcon from "@mui/icons-material/BusinessCenterTwoTone";
import EditTwoToneIcon from "@mui/icons-material/EditTwoTone";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";

import SCMain from "../SCMain/SCMain"
import SCDrawerHeader from "../SCDrawerHeader/SCDrawerHeader"
import SCAppBar from "../SCAppBar/SCAppBar"

const drawerWidth = 240;

export default function SCNavbar() {
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
      <SCAppBar position="fixed" open={open} drawerWidth={drawerWidth}>
      <Toolbar
          sx={{
            bgcolor: "#222831",
          }}
        >
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={[
              {
                mr: 2,
              },
              open && { display: "none" },
            ]}
          >
            <MenuIcon color='primary'/>
          </IconButton>
          <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ color: "#EEEEE" }}
          >
            Student & Companies
          </Typography>
        </Toolbar>
      </SCAppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
            bgcolor: "#31363F",
          },
        }}
        variant="persistent"
        anchor="left"
        open={open}
      >
        <SCDrawerHeader>   
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === "ltr" ? (
              <><ChevronLeftIcon color='primary'/><Typography
            variant="h4"
            noWrap
            component="div"
            sx={{ color: "#EEEEEE" }}
          >
            Student
          </Typography></>
            ) : (
              <ChevronRightIcon />
            )}
          </IconButton>
        </SCDrawerHeader>

        <Divider />
        <List dense={true}>
          {["Curriculum Vitae", "Internships", "Recommendation", "Drafts"].map(
            (text, index) => (
              <ListItem key={text} disablePadding>
                <ListItemButton>
                  <ListItemIcon>
                    {index % 2 === 0 ? (
                      <EditTwoToneIcon color="primary"/>
                    ) : (
                      <BusinessCenterTwoToneIcon color='primary'/>
                    )}
                  </ListItemIcon>
                  <ListItemText primary={text} sx={{ color: "#EEEEEE" }} />
                </ListItemButton>
              </ListItem>
            )
          )}
        </List>
        <Divider />
      </Drawer>
      <SCMain open={open} drawerWidth={drawerWidth}>
        <SCDrawerHeader />
          {!open ? (
            <Typography sx={{ marginBottom: 2 }}>MenuClosed</Typography>
          ) : (
            <Typography sx={{ marginBottom: 2 }}>MenuOpen</Typography>
          )}
      </SCMain>
    </Box>
  );
}
