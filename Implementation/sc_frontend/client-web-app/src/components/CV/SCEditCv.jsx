import React from "react";
import { useNavigate } from "react-router-dom";
import { useCVContext } from "./CVContext";
import {
  Grid2,
  FormControl,
  FormLabel,
  TextField,
  Button,
  Card,
  Box,
} from "@mui/material";
import * as logger from "../../logger/logger";

export const SCEditCv = () => {
  const navigate = useNavigate();
  const { cvData, setCvData } = useCVContext();

  const [newCvData, setNewCvData] = React.useState({
    id: "",
    certifications: "",
    education: "",
    project: "",
    skills: "",
    update_time: "",
    workExperience: "",
    studentID: "",
    studentName: "",
  });

  const clickBack = () => {
    navigate("/dashboard/cv");
  };

  const updateNewCvData = (field, value) => {
    setNewCvData((prevData) => ({
      ...prevData,
      [field]: value,
    }));
  };

  const updateCvData = () => {
    logger.log("updateCvData");
    // Qui puoi chiamare l'API per inviare i dati
  };

  return (
    <Box p={3}>
      {/* Grid container per la disposizione a due colonne */}
      <Grid2 container spacing={3}>
        {Object.entries(newCvData).map(([key, value]) => (
          <Grid2 item xs={12} sm={6} key={key}>
            {/* Ogni item della griglia occupa 6 spazi su 12 (2 colonne per riga) */}
            <FormControl sx={{ width: "100%" }}>
              <FormLabel>{key}</FormLabel>
              <TextField
                fullWidth
                multiline
                variant="outlined"
                value={value}
                name={key}
                onChange={(e) => updateNewCvData(key, e.target.value)}
                sx={{
                  "& .MuiOutlinedInput-root": {
                    minHeight: "auto",
                    height: "auto",
                  },
                }}
              />
            </FormControl>
          </Grid2>
        ))}
      </Grid2>

      {/* Pulsante per inviare tutti i dati */}
      <Box mt={3} display="flex" justifyContent="center">
        <Button variant="contained" color="primary" onClick={updateCvData}>
          Salva Modifiche
        </Button>
      </Box>
    </Box>
  );
};
