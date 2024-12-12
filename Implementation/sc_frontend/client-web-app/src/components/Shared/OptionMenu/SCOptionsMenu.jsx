import * as React from "react";

import MoreVertRoundedIcon from "@mui/icons-material/MoreVertRounded";
import MenuButton from "../../Templates/dashboard/components/MenuButton";
import { useGlobalContext } from "../../../global/globalContext";
import SCUserItems from "./SCUserItems";

export default function SCOptionsMenu() {
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
        <MoreVertRoundedIcon />
      </MenuButton>
      <SCUserItems
        handleClose={handleClose}
        anchorEl={anchorEl}
        open={open}
      />
    </React.Fragment>
  );
}
