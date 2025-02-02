import React, { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  FormControl,
  FormLabel,
  TextField,
  Button,
  Box,
  Badge,
  IconButton,
  badgeClasses,
  Typography,
  Alert,
} from "@mui/material";
import Grid from "@mui/material/Grid2";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { useInterviewsContext } from "./InterviewsContext";

export const SCInterviewAnswer = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const { interviewDataSnapshot, interviewID, sendInterview, setForceRender } =
    useInterviewsContext();
  const [openTipAlert, setOpenTipAlert] = useState(true);

  const questionsLabels = [
    "Answer 1",
    "Answer 2",
    "Answer 3",
    "Answer 4",
    "Answer 5",
    "Answer 6",
  ];
  const questionValues = useRef([]);

  const updateQuestionValues = (index, value) => {
    questionValues.current[index] = value;
    setForceRender((prev) => prev + 1);
  };

  const clickBack = () => {
    //NAV to interview details
    navigate(`/dashboard/interviews/details/${id}`);
  };

  return (
    <Box display="flex" flexDirection="column" height="100%" gap={2}>
      <Box sx={{ mt: 2, p: 2, border: "1px solid gray", borderRadius: 2 }}>
        <Box
          display="flex"
          paddingBottom={3}
          flexDirection="row"
          justifyContent="space-between"
          alignItems="center"
        >
          <Badge
            color="error"
            variant="dot"
            invisible={true}
            sx={{ [`& .${badgeClasses.badge}`]: { right: 2, top: 2 } }}
          >
            <IconButton size="small" onClick={clickBack}>
              <ArrowBackIcon />
            </IconButton>
          </Badge>
        </Box>
        {openTipAlert && (
          <Alert
            severity="info"
            sx={{ mb: 2 }}
            onClose={() => setOpenTipAlert(false)}
          >
            <Typography variant="h6" inline="true">
              First:
              <Typography inline="true" variant="body1" color="text.secondary">
                Type your questions for the interview.
              </Typography>
            </Typography>
            <Typography variant="h6" inline="true">
              Then:
              <Typography inline="true" variant="body1" color="text.secondary">
                Click the "Send Interview" button to send the interview to the
                candidate. After that, you will find it in the "Interview
                Templates" section available for future use.
              </Typography>
            </Typography>
            <Typography variant="h6" inline="true">
              Note:
              <Typography inline="true" variant="body1" color="text.secondary">
                Sent interviews cannot be modified.
              </Typography>
            </Typography>
          </Alert>
        )}
        <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
          <Typography variant="h2" gutterBottom>
            Interview {`ID: ` + interviewID.current + ` `}
          </Typography>
        </Box>
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {questionsLabels &&
            questionsLabels.map((value, index) => {
              return (
                <Grid
                  item="true"
                  size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                  key={value}
                >
                  <Box display="flex">
                    <FormControl sx={{ flexGrow: 1 }}>
                      <FormLabel>{value}</FormLabel>
                      <TextField
                        multiline
                        variant="outlined"
                        placeholder={`Type question ${index + 1}`}
                        name={value}
                        onBlur={(e) =>
                          updateQuestionValues(index, e.target.value)
                        }
                        sx={{
                          flexGrow: 1,
                          "& .MuiOutlinedInput-root": {
                            minHeight: "auto",
                            height: "auto",
                          },
                        }}
                      />
                    </FormControl>
                  </Box>
                </Grid>
              );
            })}
        </Grid>

        {/* Pulsante per inviare tutti i dati */}
        <Box mt={3} display="flex" justifyContent="center">
          <Button
            variant="contained"
            color="primary"
            onClick={() => {
              sendInterview(id, questionValues.current);
            }}
          >
            Send Interview
          </Button>
        </Box>
      </Box>
    </Box>
  );
};
