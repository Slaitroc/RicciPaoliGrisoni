import { styled } from "@mui/material/styles";
import { paperClasses } from "@mui/material/Paper";
import { listClasses } from "@mui/material/List";
import { useGlobalContext } from "../../../global/GlobalContext";
import { useNavigate } from "react-router-dom";
import ListItemIcon, { listItemIconClasses } from "@mui/material/ListItemIcon";
import React from "react";
import Divider, { dividerClasses } from "@mui/material/Divider";
import Menu from "@mui/material/Menu";
import MuiMenuItem from "@mui/material/MenuItem";
import ListItemText from "@mui/material/ListItemText";
import * as SCIcons from "../../Shared/SCIcons";
import * as authorization from "../../../api-calls/api-wrappers/authorization-wrapper/authorization";

const MenuItem = styled(MuiMenuItem)({
  margin: "2px 0",
});

const SCUserItems = ({ anchorEl, handleClose, open }) => {
  const { setIsAuthenticated, setUserType, setProfile, setIsEmailVerified } = useGlobalContext();
  const navigate = useNavigate();

  const clickLogout = () => {
    handleClose();
    authorization
      .logout()
      .then(() => {
        // GLOBAL STATE
        setIsAuthenticated(false);
        setUserType(null);
        setProfile(null);
        setIsEmailVerified(false);
        // NAV
        navigate("/");

      })
      .catch((err) => {
        console.error("Error during logout:", err.message);
      });
  };

  const clickProfile = () => {
    handleClose();
    // NAV
    navigate("/dashboard/profile");
  };

  const clickAccountSettings = () => {
    handleClose();
    // NAV
    navigate("/dashboard/account");
  };

  return (
    <Menu
      anchorEl={anchorEl}
      id="menu"
      open={open}
      onClose={handleClose}
      onClick={handleClose}
      transformOrigin={{ horizontal: "right", vertical: "top" }}
      anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      sx={{
        [`& .${listClasses.root}`]: {
          padding: "4px",
        },
        [`& .${paperClasses.root}`]: {
          padding: 0,
        },
        [`& .${dividerClasses.root}`]: {
          margin: "4px -4px",
        },
      }}
    >
      <MenuItem onClick={clickProfile}>Profile</MenuItem>
      <MenuItem onClick={clickAccountSettings}>Account Settings</MenuItem>
      <Divider />
      <MenuItem
        onClick={clickLogout}
        sx={{
          [`& .${listItemIconClasses.root}`]: {
            ml: "auto",
            minWidth: 0,
          },
        }}
      >
        <ListItemText>Logout</ListItemText>
        <ListItemIcon>
          <SCIcons.SCLogoutRoundedIcon fontSize="small" />
        </ListItemIcon>
      </MenuItem>
    </Menu>
  );
};

export default SCUserItems;
