import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import * as globalStatesInit from "../../global/globalStatesInit";

export const SCCountriesSel = ({ id, onChange }) => {
  return (
    <Autocomplete
      id={id}
      onChange={onChange}
      sx={{
        width: 300,
        "& .MuiAutocomplete-endAdornment .MuiIconButton-root": {
          width: "30px",
          height: "30px",
          padding: "4px",
          border: "none",
        },
      }}
      options={globalStatesInit.COUNTRIES}
      autoHighlight
      getOptionLabel={(option) => option.label}
      renderOption={(props, option) => {
        const { key, ...optionProps } = props;
        return (
          <Box
            key={key}
            component="li"
            sx={{ "& > img": { mr: 2, flexShrink: 0 } }}
            {...optionProps}
          >
            <img
              loading="lazy"
              width="20"
              srcSet={`https://flagcdn.com/w40/${option.code.toLowerCase()}.png 2x`}
              src={`https://flagcdn.com/w20/${option.code.toLowerCase()}.png`}
              alt=""
            />
            {option.label} ({option.code}) +{option.phone}
          </Box>
        );
      }}
      renderInput={(params) => (
        <TextField
          {...params}
          label=""
          slotProps={{
            htmlInput: {
              ...params.inputProps,
              autoComplete: "new-password", // disable autocomplete and autofill
            },
          }}
        />
      )}
    />
  );
};
