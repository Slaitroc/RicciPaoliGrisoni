import React from "react";
import Divider, { dividerClasses } from "@mui/material/Divider";
import Menu from "@mui/material/Menu";
import { styled } from "@mui/material/styles";
import MuiMenuItem from "@mui/material/MenuItem";
import { paperClasses } from "@mui/material/Paper";
import { listClasses } from "@mui/material/List";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon, { listItemIconClasses } from "@mui/material/ListItemIcon";
import LogoutRoundedIcon from "@mui/icons-material/LogoutRounded";
import { useGlobalContext } from "../../../global/globalContext";
import { useNavigate } from "react-router-dom";

const MenuItem = styled(MuiMenuItem)({
  margin: "2px 0",
});

const SCUserItems = ({ anchorEl, handleClose, open }) => {
  const { logout } = useGlobalContext();
  const navigate = useNavigate();

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
      <MenuItem
        onClick={() => {
          handleClose();
          navigate("/dashboard/profile");
        }}
      >
        Profile
      </MenuItem>
      <MenuItem
        onClick={() => {
          handleClose();
          navigate("/dashboard/account");
        }}
      >
        Account Settings
      </MenuItem>
      <Divider />
      <MenuItem
        onClick={() => {
          handleClose();
          logout();
        }}
        sx={{
          [`& .${listItemIconClasses.root}`]: {
            ml: "auto",
            minWidth: 0,
          },
        }}
      >
        <ListItemText>Logout</ListItemText>
        <ListItemIcon>
          <LogoutRoundedIcon fontSize="small" />
        </ListItemIcon>
      </MenuItem>
    </Menu>
  );
};

export default SCUserItems;
