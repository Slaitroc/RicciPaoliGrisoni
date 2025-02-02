import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  FormControl,
  FormLabel,
  TextField,
  Button,
  Card,
  Box,
  Badge,
  IconButton,
  badgeClasses,
  Typography,
  Alert,
} from "@mui/material";
import * as logger from "../../logger/logger";
import * as interview from "../../api-calls/api-wrappers/Interview/interview";
import Grid from "@mui/material/Grid2";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { useInterviewsContext } from "./InterviewsContext";
import NumberInput from "../Shared/InputHanlders/NumberInput";
import { DatePicker } from "@mui/x-date-pickers";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";

export const SCInterviewCheck = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const {
    interviewDataSnapshot,
    updateInterview,
    offersArray,
    interviewID,
    newOfferRef,
    openAlertProc,
    closeAlertWithDelay,
    setForceRender,
    evaluateInterview,
  } = useInterviewsContext();
  const [openTipAlert, setOpenTipAlert] = useState(true);

  const [hasAnswered, setHasAnswered] = useState(false);
  const [questions, setQuestions] = useState({});
  const [answers, setAnswers] = useState({});

  const handleFieldChange = (field, value) => {
    logger.focus(field, value);
    newOfferRef.current[field].value = value;
    setForceRender((prev) => prev + 1);
    //TODO check data
  };

  const clickBack = () => {
    //NAV to details
    navigate(`/dashboard/interviews/details/${id}`);
  };

  const fetchQuestions = async () => {
    try {
      await interview
        .getFormattedInterviewTemplateQuestions(
          interviewDataSnapshot?.interviewTemplateID.value
        )
        .then((response) => {
          if (response.success) {
            logger.focus("Fetched QUESTIONS", response.data);
            setQuestions(response.data);
            openAlertProc(response.message, "success");
            closeAlertWithDelay();
          } else {
            logger.debug("SONO DENTOR");
            openAlertProc(response.message, "error");
            closeAlertWithDelay();
          }
        });
      setAnswers(
        Object.entries({
          answer1: "Risposta alla domanda 1",
          answer2: "Risposta alla domanda 2",
          answer3: "Risposta alla domanda 3",
          answer4: "Risposta alla domanda 4",
          answer5: "Risposta alla domanda 5",
          answer6: "Risposta alla domanda 6",
        }).reduce((acc, [key, value], index) => {
          acc[`question${index + 1}`] = value;
          return acc;
        }, {})
      );
      // TODO aggiungere logica per fetchare le domande (credo conoscendo il template id) ---> vanno messe nelle label
      //await interview.
      //TODO se la domanda non ha le risposte aggiungere i bottoni passed o failed (check su hasAnswered)
      //TODO recuperare anche lo stato della interview (se è passed o failed nascondere i bottoni e mostrare la valutazione)
    } catch (e) {
      logger.error("Error fetching questions", e);
      openAlertProc(e.message, "error");
      closeAlertWithDelay();
    }
  };

  useEffect(() => {
    fetchQuestions();
  }, [interviewDataSnapshot]);

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
              Interview Evaluation:
              <Typography inline="true" variant="body1" color="text.secondary">
                Evaluate the interview by clicking on the corresponding button.
              </Typography>
            </Typography>
            <Typography variant="h6" inline="true">
              Note:
              <Typography inline="true" variant="body1" color="text.secondary">
                The evaluation is final and cannot be changed.
              </Typography>
            </Typography>
          </Alert>
        )}
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {questions &&
            Object.entries(questions).map(([key, value], index) => {
              return (
                <Grid
                  item="true"
                  size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                  key={value}
                >
                  <Box display="flex" justifyContent="center">
                    <Typography
                      variant="h6"
                      color="text.primary"
                      align="center"
                    >
                      {value}
                      <Typography
                        variant="body1"
                        color="text.secondary"
                        align="center"
                      >
                        {answers[key]}
                      </Typography>
                    </Typography>
                  </Box>
                </Grid>
              );
            })}
        </Grid>
        <Box display="flex" justifyContent="center">
          {((hasAnswered) => {
            if (hasAnswered) {
              return (
                <Box
                  mt={3}
                  display="flex"
                  flexDirection="row"
                  justifyContent="center"
                  gap={4}
                  padding={5}
                >
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => {
                      evaluateInterview(id, false);
                    }}
                  >
                    Failed
                  </Button>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => {
                      evaluateInterview(id, true);
                    }}
                  >
                    Passed
                  </Button>
                </Box>
              );
            }
            if (!hasAnswered) {
              return (
                <Box mt={6} display="flex" justifyContent="center">
                  <Typography variant="h6" color="text.primary" align="center">
                    Interview has been sent! ✈️
                    <Typography
                      variant="body1"
                      color="text.secondary"
                      align="center"
                    >
                      Waiting for student answers...
                    </Typography>
                  </Typography>
                </Box>
              );
            }
            return;
          })(hasAnswered)}
        </Box>
      </Box>
    </Box>
  );
};
