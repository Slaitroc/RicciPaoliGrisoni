import * as React from "react";
import MuiAvatar from "@mui/material/Avatar";
import MuiListItemAvatar from "@mui/material/ListItemAvatar";
import MenuItem from "@mui/material/MenuItem";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListSubheader from "@mui/material/ListSubheader";
import Select, { selectClasses } from "@mui/material/Select";
import { styled } from "@mui/material/styles";
import DevicesRoundedIcon from "@mui/icons-material/DevicesRounded";
import SmartphoneRoundedIcon from "@mui/icons-material/SmartphoneRounded";
import FormControl from "@mui/material/FormControl";

const Avatar = styled(MuiAvatar)(({ theme }) => ({
  width: 28,
  height: 28,
  backgroundColor: (theme.vars || theme).palette.background.paper,
  color: (theme.vars || theme).palette.text.secondary,
  border: `1px solid ${(theme.vars || theme).palette.divider}`,
}));

const ListItemAvatar = styled(MuiListItemAvatar)({
  minWidth: 0,
  marginRight: 12,
});

export default function SCSelectLogin() {
  const [login, setLogin] = React.useState("");

  const handleChange = (event) => {
    setLogin(event.target.value);
  };

  return (
    <>
      <FormControl>
        <InputLabel
          id="login-select-label"
          sx={{
            textAlign: "center", // Allinea il testo al centro
            width: "100%", // Assicura che il contenitore copra l'intera larghezza
            transform: "translateY(-50%)",
            top: "50%",
          }}
        >
          {/* Login Options */}
        </InputLabel>
        <Select
          labelId="login-select-label"
          id="login-select"
          value={login}
          onChange={handleChange}
          displayEmpty
          // inputProps={{ "aria-label": "Select login" }}
          fullWidth
          label="Age"
          sx={{
            maxHeight: 56,
            width: 215,
            "&.MuiList-root": {
              p: "8px",
            },
            [`& .${selectClasses.select}`]: {
              display: "flex",
              alignItems: "center",
              gap: "2px",
              pl: 1,
            },
          }}
        >
          <ListSubheader sx={{ pt: 0 }}>Select Login</ListSubheader>
          <MenuItem value="">
            <ListItemAvatar>
              <Avatar alt="Sitemark web">
                <DevicesRoundedIcon sx={{ fontSize: "1rem" }} />
              </Avatar>
            </ListItemAvatar>
            <ListItemText primary="Company Login" />
          </MenuItem>
          <MenuItem value={10}>
            <ListItemAvatar>
              <Avatar alt="Sitemark App">
                <SmartphoneRoundedIcon sx={{ fontSize: "1rem" }} />
              </Avatar>
            </ListItemAvatar>
            <ListItemText primary="Student Login" />
          </MenuItem>
          <MenuItem value={20}>
            <ListItemAvatar>
              <Avatar alt="Sitemark Store">
                <DevicesRoundedIcon sx={{ fontSize: "1rem" }} />
              </Avatar>
            </ListItemAvatar>
            <ListItemText primary="University Login" />
          </MenuItem>
        </Select>
      </FormControl>
    </>
  );
}
