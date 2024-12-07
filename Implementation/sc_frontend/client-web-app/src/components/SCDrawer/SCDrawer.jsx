import React from "react";
import Divider from "@mui/material/Divider";
import BusinessCenterTwoToneIcon from "@mui/icons-material/BusinessCenterTwoTone";
import EditTwoToneIcon from "@mui/icons-material/EditTwoTone";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import { useTheme } from "@mui/material";
import SCDrawerHeader from "../SCDrawerHeader/SCDrawerHeader";


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
                  <ListItemText primary={text} sx={{ color: (theme)=>theme.palette.text.primary }} />
                </ListItemButton>
              </ListItem>
            )
          )}
        </List>
        <Divider />
      </Drawer>
    )

}

export default SCDrawer;