import * as React from "react";
import { useState } from "react";
import {
  Avatar,
  Box,
  Button,
  Card,
  FormControl,
  FormLabel,
  TextField,
} from "@mui/material";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";
import SCUploadImage from "../Shared/SCUploadImage";
import { useGlobalContext } from "../../global/globalContext";

const key = uuidv4();

export default function SCCv() {
  const { previewUrl } = useGlobalContext();

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

  const [showEdit, setShowEdit] = useState(false);

  const onEditClick = (bool) => {
    return () => setShowEdit(bool);
  };

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
        ? {
            ...item,
            content: temporalData.find((tempItem) => tempItem.id === id)
              .content,
          }
        : item
    );
    setCvData(updatedCvData);
  };

  return (
    <>
      <Box display="flex" flexDirection="column" height="100%" gap={2}>
        <Box sx={{ mt: 4, p: 2, border: "1px solid gray", borderRadius: 2 }}>
          <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
            <Avatar src={previewUrl} alt="Preview" />
            <Typography variant="h5" gutterBottom>
              CV Summary
            </Typography>
          </Box>
          {cvData.map((cv) => (
            <Box key={uuidv4()} id={cv.id} sx={{ mb: 2 }}>
              <Typography variant="h6">{cv.title}</Typography>
              <Typography variant="body1" whiteSpace="pre-line">
                {cv.content || "No content provided."}
              </Typography>
            </Box>
          ))}
        </Box>
        <Box display="flex" justifyContent="center">
          <Button
            variant="contained"
            onClick={onEditClick(!showEdit)}
            sx={{
              width: "20%",
            }}
          >
            Edit CV
          </Button>
        </Box>
        {showEdit && (
          <>
            <SCUploadImage />
            {cvData.map((cv) => {
              return (
                <React.Fragment key={key}>
                  <Box display="flex" flexDirection="row" gap={6}>
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
                      <Box display="flex" justifyContent="left" padding={2}>
                        <Button
                          onClick={() => updateCvData(cv.id)}
                          variant="outlined"
                          sx={{
                            width: "20%",
                            height: "60%",
                            // "&.MuiButton-root": {
                            //   border: "1px solid grey", // Modifica solo questo bottone
                            // },
                          }}
                        >
                          Update
                        </Button>
                      </Box>
                    </FormControl>
                    <FormControl sx={{ width: "100%" }}>
                      <FormLabel>{cv.title}</FormLabel>
                      <Card sx={{ width: "100%", whiteSpace: "pre-line" }}>
                        {" "}
                        {cv.content}
                      </Card>
                    </FormControl>
                  </Box>
                </React.Fragment>
              );
            })}
          </>
        )}
      </Box>
    </>
  );
}
