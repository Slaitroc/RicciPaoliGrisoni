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
import { FormLabel, InputLabel } from "@mui/material";
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

export default function SCSelectLogin({ setUserType }) {
  const [selection, setSelection] = React.useState("");

  const handleChange = (event) => {
    const selectedValue = event.target.value;
    const selectedOption = options.find(
      (option) => option.value === selectedValue
    );
    if (selectedOption) {
      setUserType(selectedOption.type);
    }
    setSelection(selectedValue);
  };

  const options = [
    {
      value: "0",
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
        <FormLabel component="legend">Select User Type</FormLabel>
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
          value={selection}
          onChange={handleChange}
          fullWidth
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
          <ListSubheader sx={{ pt: 0 }}>User Types</ListSubheader>
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
