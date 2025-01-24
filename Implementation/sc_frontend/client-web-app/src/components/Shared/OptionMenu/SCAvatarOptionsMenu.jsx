import * as React from "react";
import { styled } from "@mui/material/styles";
import MuiMenuItem from "@mui/material/MenuItem";
import MenuButton from "../../Templates/dashboard/components/MenuButton";
import Avatar from "@mui/material/Avatar";
import SCUserItems from "./SCUserItems";
import { useGlobalContext } from "../../../global/GlobalContext";

export default function SCAvatarOptionsMenu() {
  const { previewUrl } = useGlobalContext();
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
        sx={{ border: "none" }}
      >
        <Avatar
          sizes="small"
          alt="User Name"
          src={previewUrl}
          sx={{ width: 36, height: 36 }}
        />
      </MenuButton>
      <SCUserItems open={open} anchorEl={anchorEl} handleClose={handleClose} />
    </React.Fragment>
  );
}
