import * as React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Stack from "@mui/material/Stack";
import { v4 as uuidv4 } from "uuid";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as SCIcons from "../Shared/SCIcons";
import { useGlobalContext } from "../../global/globalContext";
import * as Global from "../../global/globalStatesInit";

const studentListItems = [
  {
    key: uuidv4(),
    text: "Overview",
    icon: <SCIcons.SCDashboardIcon />,
    route: "/dashboard",
  },
  {
    key: uuidv4(),
    text: "Cv",
    icon: <SCIcons.SCAccountBoxIcon />,
    route: "/dashboard/cv",
  },
  {
    key: uuidv4(),
    text: "Spontaneous Applications",
    icon: <SCIcons.SCSendIcon />,
    route: "/dashboard/applications",
  },
  {
    key: uuidv4(),
    text: "Recommendation Process",
    icon: <SCIcons.SCJoinInnerIcon />,
    route: "/dashboard/recommendations",
  },
  {
    key: uuidv4(),
    text: "Interviews",
    icon: <SCIcons.SCCollectionsBookmarkIcon />,
    route: "/dashboard/interviews",
  },
  {
    key: uuidv4(),
    text: "Confirmed Internship",
    icon: <SCIcons.SCApartmentIcon />,
    route: "/dashboard/confirmed-internships",
  },
  {
    key: uuidv4(),
    text: "Communications",
    icon: <SCIcons.SCInboxIcon />,
    route: "/dashboard/communications",
  },
  // {
  //   key: uuidv4(),
  //   text: "Swipe",
  //   icon: <SCIcons.SCHomeIcon />,
  //   route: "/dashboard/swipe-card",
  // },
];

const univesityListItems = [
  {
    key: uuidv4(),
    text: "Overview",
    icon: <SCIcons.SCDashboardIcon />,
    route: "/dashboard",
  },
  {
    key: uuidv4(),
    text: "University",
    icon: <SCIcons.SCAccountBoxIcon />,
    route: "/dashboard/university",
  },
  {
    key: uuidv4(),
    text: "Recommendations Stats",
    icon: <SCIcons.SCJoinInnerIcon />,
    route: "/dashboard/recommendations",
  },
  {
    key: uuidv4(),
    text: "Interviews Stats",
    icon: <SCIcons.SCCollectionsBookmarkIcon />,
    route: "/dashboard/interviews",
  },
  {
    key: uuidv4(),
    text: "Confirmed Internship",
    icon: <SCIcons.SCApartmentIcon />,
    route: "/dashboard/confirmed-internships",
  },
  {
    key: uuidv4(),
    text: "Communications",
    icon: <SCIcons.SCInboxIcon />,
    route: "/dashboard/communications",
  },
];

const companyListItems = [
  {
    key: uuidv4(),
    text: "Overview",
    icon: <SCIcons.SCDashboardIcon />,
    route: "/dashboard",
  },
  {
    key: uuidv4(),
    text: "Internship Offers",
    icon: <SCIcons.SCAccountBoxIcon />,
    route: "/dashboard/internship-offers",
  },
  {
    key: uuidv4(),
    text: "Spontaneous Applications",
    icon: <SCIcons.SCInboxIcon />,
    route: "/dashboard/applications",
  },
  {
    key: uuidv4(),
    text: "Recommendation Process",
    icon: <SCIcons.SCJoinInnerIcon />,
    route: "/dashboard/recommendations",
  },
  {
    key: uuidv4(),
    text: "Interviews",
    icon: <SCIcons.SCCollectionsBookmarkIcon />,
    route: "/dashboard/interviews",
  },
  {
    key: uuidv4(),
    text: "Confirmed Internship",
    icon: <SCIcons.SCApartmentIcon />,
    route: "/dashboard/confirmed-internships",
  },
  {
    key: uuidv4(),
    text: "Communications",
    icon: <SCIcons.SCInboxIcon />,
    route: "/dashboard/communications",
  },
  {
    key: uuidv4(),
    text: "Swipe",
    icon: <SCIcons.SCHomeIcon />,
    route: "/dashboard/swipe-card",
  },
];

const secondaryListItems = [
  {
    key: uuidv4(),
    text: "Settings",
    icon: <SCIcons.SCSettingsRoundedIcon />,
    route: "/dashboard/settings",
  },
  {
    key: uuidv4(),
    text: "S&C Home",
    icon: <SCIcons.SCHomeIcon />,
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

  const { userType } = useGlobalContext();

  const userListItems = () => {
    switch (userType) {
      case Global.student:
        return studentListItems;
      case Global.university:
        return univesityListItems;
      case Global.company:
        return companyListItems;
      default:
        return [];
    }
  };

  return (
    <Stack sx={{ flexGrow: 1, p: 1, justifyContent: "space-between" }}>
      <List dense>
        {userListItems().map((item) => (
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
