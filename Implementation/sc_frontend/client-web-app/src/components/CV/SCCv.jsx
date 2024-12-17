import * as React from "react";
import { useState } from "react";
import {
  Box,
  Button,
  Card,
  FormControl,
  FormLabel,
  IconButton,
  TextField,
} from "@mui/material";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";

export default function SCCv() {
  const [cvData, setCvData] = useState([
    { id: 0, title: "Personal Information", content: "" },
    { id: 1, title: "Contacts", content: "" },
    { id: 2, title: "Education", content: "" },
    { id: 3, title: "Work Experience", content: "" },
    { id: 4, title: "Courses", content: "" },
    { id: 5, title: "Projects", content: "" },
    { id: 6, title: "Languages", content: "" },
    { id: 7, title: "Digital & IT Skills", content: "" },
    { id: 8, title: "Interests", content: "" },
    { id: 9, title: "Publications", content: "" },
    {
      id: 10,
      title: "Authorization for Personal Data Processing",
      content: "",
    },
  ]);

  const [temporalData, setTemporalData] = useState(
    cvData.map((item) => ({ id: item.id, content: item.content }))
  );

  const updateTemporalData = (id, event) => {
    const newContent = event.target.value;
    const updatedCvData = temporalData.map((item) =>
      item.id === id ? { ...item, content: newContent } : item
    );
    setTemporalData(updatedCvData);
  };

  const updateCvData = (id) => {
    const updatedCvData = cvData.map((item) =>
      item.id === id
        ? temporalData.find((tempItem) => tempItem.id === id)
        : item
    );
    setCvData(updatedCvData);
  };

  // devo aggiornare i dati nelle card:
  // opzione 1: quando l'utente scrive voglio aggiornare in tempo reale il contenuto della card
  // opzione 2: quando l'utente clicca su un bottone voglio aggiornare il contenuto della card in modo da vedere le modifiche

  /* opzione 1:
    mi serve un solo oggetto nello state 
    quando modifico il testo tramite il valore on change del testo devo aggiornare il contenuto della card chiamando un metodo 
    che modifica coerentemente l'oggetto state 

    Come faccio a identificare il campo da modificare nell'oggetto state? 
    posso passare la key dell'oggetto come parametro della funzione di modifica

  */
  return (
    <>
      <Box display="flex" flexDirection="column" height="100%" gap={2}>
        {cvData.map((cv) => {
          return (
            <Box display="flex" flexDirection="row" gap={4}>
              <FormControl sx={{ width: "100%" }}>
                <FormLabel>{cv.title}</FormLabel>
                <TextField
                  multiline
                  variant="outlined"
                  placeholder={cv.title}
                  onChange={(e) => updateTemporalData(cv.id, e)}
                  sx={{
                    "& .MuiOutlinedInput-root": {
                      minHeight: "auto", // Altezza minima dinamica
                      height: "auto", // Altezza complessiva non fissa
                    },
                  }}
                />
                <Button onClick={() => updateCvData(cv.id)}>Update</Button>
              </FormControl>
              <FormControl sx={{ width: "100%" }}>
                <FormLabel>{cv.title}</FormLabel>
                <Card sx={{ width: "100%" }}> {cv.content}</Card>
              </FormControl>
            </Box>
          );
        })}
        <Box sx={{ mt: 4, p: 2, border: "1px solid gray", borderRadius: 2 }}>
          <Typography variant="h5" gutterBottom>
            CV Summary
          </Typography>
          {cvData.map((cv) => (
            <Box key={cv.id} sx={{ mb: 2 }}>
              <Typography variant="h6">{cv.title}</Typography>
              <Typography variant="body1">
                {cv.content || "No content provided."}
              </Typography>
            </Box>
          ))}
        </Box>
      </Box>
    </>
  );
}
