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
import * as logger from "../../logger/logger";

export const SCInterviewAnswer = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const {
    interviewDataSnapshot,
    interviewID,
    sendAnswers,
    questions,
    answers,
  } = useInterviewsContext();
  const [openTipAlert, setOpenTipAlert] = useState(true);

  const [refUdate, setRefUpdate] = useState(0);

  useEffect(() => {});

  const answerValues = useRef([]);

  const updateQuestionValues = (index, value) => {
    answerValues.current[index] = value;
    setRefUpdate((prev) => prev + 1);
  };

  useEffect(() => {
    logger.debug("Answer REF", answerValues);
  }, []);

  const clickBack = () => {
    //NAV to interview details
    navigate(`/dashboard/interviews/details/${id}`);
  };

  let answerIndex = 1;

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
          {/* BACK BUTTON */}
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
        {/* TIP ALERT */}
        {openTipAlert && (
          <Alert
            severity="info"
            sx={{ mb: 2 }}
            onClose={() => setOpenTipAlert(false)}
          >
            <Typography variant="h6" inline="true">
              First:
              <Typography inline="true" variant="body1" color="text.secondary">
                Type your answers for the interview.
              </Typography>
            </Typography>
            <Typography variant="h6" inline="true">
              Then:
              <Typography inline="true" variant="body1" color="text.secondary">
                Click the "Send Answers" button to send the answers to the
                candidate. After that, you will find it in the Interview detail
                section.
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
            Interview {`ID: ` + interviewDataSnapshot?.id.value + ` `}
          </Typography>
        </Box>
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {questions &&
            Object.entries(questions).map((value, index) => {
              let currentIndex = answerIndex;
              if (value[0] !== "id")
                return (
                  <Grid
                    item="true"
                    size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                    key={value[1].serverValue}
                  >
                    <Box display="flex">
                      <FormControl sx={{ flexGrow: 1 }}>
                        <FormLabel>{value[1].value}</FormLabel>
                        <TextField
                          multiline
                          variant="outlined"
                          placeholder={`Type question ${answerIndex++}`}
                          name={value}
                          onBlur={(e) =>
                            updateQuestionValues(--currentIndex, e.target.value)
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
              sendAnswers(id, answerValues.current);
            }}
          >
            Send Interview
          </Button>
        </Box>
      </Box>
    </Box>
  );
};
