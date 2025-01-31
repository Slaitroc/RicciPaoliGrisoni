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
import { useGlobalContext } from "../../global/GlobalContext";
import { useCVContext } from "./CVContext";
import { useNavigate } from "react-router-dom";

const key = uuidv4();

export default function SCCv() {
  const navigate = useNavigate();
  const { previewUrl, profile } = useGlobalContext();
  const { cvData, setCvData } = useCVContext();
  // const [temporalData, setTemporalData] = useState(
  //   cvData ? cvData.map((item) => ({ id: item.id, content: item.content })) : []
  // );

  const [showEdit, setShowEdit] = useState(false);

  const onEditClick = () => {
    //NAV to CV edit
    navigate("/dashboard/cv/edit");
    // return () => setShowEdit(bool);
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
        <Box sx={{ mt: 2, p: 2, border: "1px solid gray", borderRadius: 2 }}>
          <Box
            display="flex"
            flexDirection="row"
            justifyContent="space-between"
          >
            <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
              <Avatar src={previewUrl} alt="Preview" />
              <Typography variant="h5" gutterBottom>
                CV Summary
              </Typography>
            </Box>
            <Box display="flex" flexDirection="column" alignItems="end">
              <Typography variant="h6 " gutterBottom>
                Last Update:{" "}
                {new Date(cvData.updateTime.value).toLocaleString("it-IT", {
                  year: "numeric",
                  month: "2-digit",
                  day: "2-digit",
                  hour: "2-digit",
                  minute: "2-digit",
                })}
              </Typography>
              <Button
                variant="outlined"
                onClick={onEditClick}
                align="left"
                sx={{
                  whiteSpace: "nowrap",
                  width: "50%",
                  paddingX: 6,
                }}
              >
                Edit CV
              </Button>
            </Box>
          </Box>
          <Box key={uuidv4()} sx={{ mb: 2 }}>
            <Box display="flex" flexDirection="column" gap={1}>
              <Typography variant="h6">Student Name:</Typography>
              <Typography
                variant="body1"
                whiteSpace="pre-line"
                color="text.secondary"
              >
                {profile.name + " " + profile.surname}
              </Typography>
            </Box>
          </Box>
          {Object.entries(cvData).map((cv) => {
            if (
              cv[0] === "studentID" ||
              cv[0] === "id" ||
              cv[0] === "updateTime" ||
              cv[0] === "studentName"
            )
              return null;
            return (
              <Box key={uuidv4()} id={cv.id} sx={{ mb: 2 }}>
                <Box display="flex" flexDirection="column" gap={1}>
                  <Typography variant="h6">{cv[1].label}:</Typography>
                  <Typography
                    variant="body1"
                    whiteSpace="pre-line"
                    color="text.secondary"
                  >
                    {cv[1].value || "No content provided."}
                  </Typography>
                </Box>
              </Box>
            );
          })}
        </Box>
      </Box>
    </>
  );
}
