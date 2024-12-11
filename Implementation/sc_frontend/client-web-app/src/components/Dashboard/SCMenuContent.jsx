import * as React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Stack from "@mui/material/Stack";
import HomeRoundedIcon from "@mui/icons-material/HomeRounded";
import AnalyticsRoundedIcon from "@mui/icons-material/AnalyticsRounded";
import PeopleRoundedIcon from "@mui/icons-material/PeopleRounded";
import AssignmentRoundedIcon from "@mui/icons-material/AssignmentRounded";
import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";
import InfoRoundedIcon from "@mui/icons-material/InfoRounded";
import HelpRoundedIcon from "@mui/icons-material/HelpRounded";
import { v4 as uuidv4 } from "uuid";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const mainListItems = [
  {
    key: uuidv4(),
    text: "Dashboard",
    icon: <AnalyticsRoundedIcon />,
    route: "/dashboard",
  },
  {
    key: uuidv4(),
    text: "Contact",
    icon: <AssignmentRoundedIcon />,
    route: "/dashboard/contacts",
  },
];

const secondaryListItems = [
  {
    key: uuidv4(),
    text: "Settings",
    icon: <SettingsRoundedIcon />,
    route: "/dashboard/settings",
  },
  {
    key: uuidv4(),
    text: "About",
    icon: <InfoRoundedIcon />,
    route: "/dashboard/about",
  },
  {
    key: uuidv4(),
    text: "Feedback",
    icon: <HelpRoundedIcon />,
    route: "/dashboard/feedback",
  },
  {
    key: uuidv4(),
    text: "S&C Home",
    icon: <HelpRoundedIcon />,
    route: "/",
  },
];

export default function SCMenuContent() {
  const [selectedItem, setSelectedItem] = useState("home");
  const navigate = useNavigate();

  const handleItemClick = (item) => {
    setSelectedItem(item.key);
    navigate(item.route);
  };

  return (
    <Stack sx={{ flexGrow: 1, p: 1, justifyContent: "space-between" }}>
      <List dense>
        {mainListItems.map((item) => (
          <ListItem key={item.key} disablePadding sx={{ display: "block" }}>
            <ListItemButton
              onClick={() => handleItemClick(item)}
              selected={selectedItem === item.key}
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>

      <List dense>
        {secondaryListItems.map((item) => (
          <ListItem key={item.key} disablePadding sx={{ display: "block" }}>
            <ListItemButton
              onClick={() => handleItemClick(item)}
              selected={selectedItem === item.key}
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Stack>
  );
}
