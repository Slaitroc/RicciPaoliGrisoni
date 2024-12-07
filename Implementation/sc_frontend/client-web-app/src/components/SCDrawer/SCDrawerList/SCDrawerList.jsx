import React from "react";
import BusinessCenterTwoToneIcon from "@mui/icons-material/BusinessCenterTwoTone";
import EditTwoToneIcon from "@mui/icons-material/EditTwoTone";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import List from "@mui/material/List";
import { useTheme } from "@mui/material";



const SCDrawerList = () => {
    const theme = useTheme();
    return (
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
    )
}
export default SCDrawerList;