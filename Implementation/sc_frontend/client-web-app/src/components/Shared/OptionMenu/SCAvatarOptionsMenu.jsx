import * as React from "react";
import { styled } from "@mui/material/styles";
import Divider, { dividerClasses } from "@mui/material/Divider";
import Menu from "@mui/material/Menu";
import MuiMenuItem from "@mui/material/MenuItem";
import { paperClasses } from "@mui/material/Paper";
import { listClasses } from "@mui/material/List";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon, { listItemIconClasses } from "@mui/material/ListItemIcon";
import LogoutRoundedIcon from "@mui/icons-material/LogoutRounded";
import MoreVertRoundedIcon from "@mui/icons-material/MoreVertRounded";
import MenuButton from "../../Templates/dashboard/components/MenuButton";
import Avatar from "@mui/material/Avatar";
import SCUserItems from "./SCUserItems";

const MenuItem = styled(MuiMenuItem)({
  margin: "2px 0",
});

export default function SCAvatarOptionsMenu() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <React.Fragment>
      <MenuButton
        aria-label="Open menu"
        onClick={handleClick}
        sx={{ borderColor: "transparent" }}
      >
        <Avatar
          sizes="small"
          alt="User Name"
          src="/static/images/avatar/7.jpg"
          sx={{ width: 36, height: 36 }}
        />
      </MenuButton>
      <SCUserItems open={open} anchorEl={anchorEl} handleClose={handleClose} />
    </React.Fragment>
  );
}
