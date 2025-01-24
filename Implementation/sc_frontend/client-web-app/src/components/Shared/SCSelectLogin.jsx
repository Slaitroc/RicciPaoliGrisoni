import * as React from "react";
import MuiAvatar from "@mui/material/Avatar";
import MuiListItemAvatar from "@mui/material/ListItemAvatar";
import MenuItem from "@mui/material/MenuItem";
import ListItemText from "@mui/material/ListItemText";
import ListSubheader from "@mui/material/ListSubheader";
import Select, { selectClasses } from "@mui/material/Select";
import { styled } from "@mui/material/styles";
import DevicesRoundedIcon from "@mui/icons-material/DevicesRounded";
import SmartphoneRoundedIcon from "@mui/icons-material/SmartphoneRounded";
import FormControl from "@mui/material/FormControl";
import { InputLabel } from "@mui/material";
import { useGlobalContext } from "../../global/GlobalContext";

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
  const [login, setLogin] = React.useState("10");
  const { setUserType } = useGlobalContext();
  const handleChange = (event) => {
    const selectedValue = event.target.value;
    const selectedType = options.find(
      (option) => option.value === selectedValue
    )?.type;
    if (selectedType) {
      setUserType(selectedType);
    }
    setLogin(selectedValue);
  };

  const options = [
    {
      value: "",
      label: "Company SignUp",
      icon: <DevicesRoundedIcon />,
      type: "company",
    },
    {
      value: 10,
      label: "Student SignUp",
      icon: <SmartphoneRoundedIcon />,
      type: "student",
    },
    {
      value: 20,
      label: "University SignUp",
      icon: <DevicesRoundedIcon />,
      type: "university",
    },
  ];

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
          {options.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              <ListItemAvatar>
                <Avatar alt={option.label}>{option.icon}</Avatar>
              </ListItemAvatar>
              <ListItemText primary={option.label} />
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </>
  );
}
