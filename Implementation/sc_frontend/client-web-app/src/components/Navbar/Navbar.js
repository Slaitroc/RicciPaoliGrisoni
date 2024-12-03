import * as React from "react";
import { styled, useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import CssBaseline from "@mui/material/CssBaseline";
import MuiAppBar from "@mui/material/AppBar";
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
import InboxIcon from "@mui/icons-material/MoveToInbox";
import MailIcon from "@mui/icons-material/Mail";

const drawerWidth = 240;

const Main = styled("main", { shouldForwardProp: (prop) => prop !== "open" })(
  ({ theme }) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: `-${drawerWidth}px`,
    variants: [
      {
        props: ({ open }) => open,
        style: {
          transition: theme.transitions.create("margin", {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
          }),
          marginLeft: 0,
        },
      },
    ],
  })
);

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme }) => ({
  transition: theme.transitions.create(["margin", "width"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  variants: [
    {
      props: ({ open }) => open,
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

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
  justifyContent: "flex-end",
}));

export default function Navbar() {
  //Use React Hook to load the used theme
  const theme = useTheme();
  //Create a "open" variable and a "editing function" setOpen. Set the default as false
  const [open, setOpen] = React.useState(false);

  //Declare constant variable that store the function to that change the state of "open". 
  //yep u can save function in a variable in js.
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  //Return a Box containing children component like the menu Icon and the Drawer when the NavBar is open or the "top bar" of the site. 
  //Toolbar is the top bar on the webpage (the one containing the Student&Company and the iconButton) contained inside 

  //Drawer is the menu itself compose of a list of element and either a "EditTwoIcon" or "BusinessCenterTwoToneIcon" based on the item index
  //Drawer is visible only when the field "open" is set to true by the component open created before and accesses with {open} 
  //({variable} is mandatory to embed JS expression in a JSX component. See the last lines to understand js in JSX better
  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar position="fixed" open={open}>
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
      </AppBar>
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
        <DrawerHeader>   
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
        </DrawerHeader>

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
      <Main open={open}>
        <DrawerHeader />
        {!open ? (
          <Typography sx={{ marginBottom: 2 }}>MenuClosed</Typography>
        ) : (
          <Typography sx={{ marginBottom: 2 }}>MenuOpen</Typography>
        )}
      </Main>
    </Box>
  );
}
