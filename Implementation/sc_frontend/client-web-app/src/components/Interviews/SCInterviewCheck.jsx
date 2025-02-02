import React, { useEffect, useState, useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useInterviewsContext } from "./InterviewsContext";
import { useGlobalContext } from "../../global/GlobalContext";
import {
  COMPANY_USER_TYPE,
  STUDENT_USER_TYPE,
} from "../../global/globalStatesInit";
import {
  Button,
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
import NumberInput from "../Shared/InputHanlders/NumberInput";

export const SCInterviewCheck = () => {
  const navigate = useNavigate();
  const { userType } = useGlobalContext();
  const { id } = useParams();
  const {
    interviewDataSnapshot,
    setForceRender,
    fetchedData,
    clickBackToDetails,
    evaluateInterview,
    setInterviewDataSnapshot,
    questions,
    answers,
  } = useInterviewsContext();
  const [openTipAlert, setOpenTipAlert] = useState(true);

  const [hasAnswered, setHasAnswered] = useState(false);

  const evaluationRef = useRef(0);

  const returnGridElement = (status, hasAnswered) => {
    if (userType === STUDENT_USER_TYPE) {
      if (status === "toBeSubmitted") {
        return;
      } else if (status === "submitted" && hasAnswered === "false") {
        return;
      } else if (status === "passed" || status === "failed") {
        return;
      } // TODO
      else if (status === "submitted" && hasAnswered === "true") {
        return (
          <>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  1. {questions?.question1.value}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewDataSnapshot?.hasAnswered.value &&
                    answers?.answer1.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  2. {questions?.question2.value}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewDataSnapshot?.hasAnswered.value &&
                    answers?.answer2.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  3. {questions?.question3.value}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewDataSnapshot?.hasAnswered.value &&
                    answers?.answer3.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  4. {questions?.question4.value}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewDataSnapshot?.hasAnswered.value &&
                    answers?.answer4.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  5. {questions?.question5.value}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewDataSnapshot?.hasAnswered.value &&
                    answers?.answer5.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  6. {questions?.question6.value}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewDataSnapshot?.hasAnswered.value &&
                    answers?.answer6.value}
                </Typography>
              </Box>
            </Grid>
          </>
        );
      }
    } else if (userType === COMPANY_USER_TYPE) {
      if (status === "toBeSubmitted") {
        return;
      } else if (status === "submitted" && hasAnswered === "true") {
        return;
      } // TODO
      else if (status === "submitted" && hasAnswered === "false") {
        return (
          <>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  1. {questions?.question1.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  2. {questions?.question2.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  3. {questions?.question3.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  4. {questions?.question4.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  5. {questions?.question5.value}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1.value}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  6. {questions?.question6.value}
                </Typography>
              </Box>
            </Grid>
          </>
        );
      }
    }
  };

  const returnAlertTip = (status, hasAnswered) => {
    if ((status, hasAnswered)) {
      if (userType === STUDENT_USER_TYPE) {
        //page is unreachable in this state
        if (status === "toBeSubmitted") return;
        //page is unreachable in this state
        else if (status === "submitted" && hasAnswered === "false") return;
        else if (status === "passed" || status === "failed")
          return (
            <Alert
              severity="info"
              sx={{ mb: 2 }}
              onClose={() => setOpenTipAlert(false)}
            >
              <Typography variant="h6" inline="true">
                Interview Evaluated!
                <Typography
                  inline="true"
                  variant="body1"
                  color="text.secondary"
                >
                  See the outcome in the interview details.
                </Typography>
              </Typography>
              <Typography variant="h6" inline="true">
                Note:
                <Typography
                  inline="true"
                  variant="body1"
                  color="text.secondary"
                >
                  The evaluation is final and cannot be changed.
                </Typography>
              </Typography>
            </Alert>
          );
        else if (status === "submitted" && hasAnswered === "true")
          return (
            <Alert
              severity="info"
              sx={{ mb: 2 }}
              onClose={() => setOpenTipAlert(false)}
            >
              <Typography variant="h6" inline="true">
                Wait for the evaluation...
              </Typography>
              <Typography variant="h6" inline="true">
                Note:
                <Typography
                  inline="true"
                  variant="body1"
                  color="text.secondary"
                >
                  Your answers cannot be changed.
                </Typography>
              </Typography>
            </Alert>
          );
      } else if (userType === COMPANY_USER_TYPE) {
        //page is unreachable in this state
        if (status === "toBeSubmitted") return;
        else if (status === "submitted" && hasAnswered === "true")
          return (
            <Alert
              severity="info"
              sx={{ mb: 2 }}
              onClose={() => setOpenTipAlert(false)}
            >
              <Typography variant="h6" inline="true">
                Interview Evaluation:
                <Typography
                  inline="true"
                  variant="body1"
                  color="text.secondary"
                >
                  Evaluate the interview by clicking on the corresponding
                  button.
                </Typography>
              </Typography>
              <Typography variant="h6" inline="true">
                Note:
                <Typography
                  inline="true"
                  variant="body1"
                  color="text.secondary"
                >
                  The evaluation is final and cannot be changed.
                </Typography>
              </Typography>
            </Alert>
          );
        else if (status === "submitted" && hasAnswered === "false")
          return (
            <Alert
              severity="info"
              sx={{ mb: 2 }}
              onClose={() => setOpenTipAlert(false)}
            >
              <Typography variant="h6" inline="true">
                Just wait for the student answers...
              </Typography>
              <Typography variant="h6" inline="true">
                Note:
                <Typography
                  inline="true"
                  variant="body1"
                  color="text.secondary"
                >
                  Answers and evaluation are final and cannot be changed.
                </Typography>
              </Typography>
            </Alert>
          );
      }
    } else return;
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
          {/* BACK TO DETAILS */}
          <Badge
            color="error"
            variant="dot"
            invisible={true}
            sx={{ [`& .${badgeClasses.badge}`]: { right: 2, top: 2 } }}
          >
            <IconButton size="small" onClick={clickBackToDetails}>
              <ArrowBackIcon />
            </IconButton>
          </Badge>
        </Box>
        {openTipAlert &&
          returnAlertTip(
            interviewDataSnapshot?.status.value,
            interviewDataSnapshot?.hasAnswered.value
          )}
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {returnGridElement(
            interviewDataSnapshot?.status.value,
            interviewDataSnapshot?.hasAnswered.value
          )}
        </Grid>
        <Box display="flex" justifyContent="center">
          {((hasAnswered) => {
            if (userType === COMPANY_USER_TYPE) {
              if (hasAnswered) {
                return (
                  <Box
                    mt={3}
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    gap={4}
                    padding={5}
                  >
                    <NumberInput
                      label="Evaluation"
                      step={1}
                      min={0}
                      max={5}
                      allowWheelScrub={true}
                      defaultValue={0}
                      onValueChange={(value, event) =>
                        (evaluationRef.current = value)
                      }
                    />
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() =>
                        evaluateInterview(id, evaluationRef.current)
                      }
                    >
                      Send Evaluation
                    </Button>
                  </Box>
                );
              }
              if (!hasAnswered) {
                return (
                  <Box mt={6} display="flex" justifyContent="center">
                    <Typography
                      variant="h6"
                      color="text.primary"
                      align="center"
                    >
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
            }
            if (userType === STUDENT_USER_TYPE) {
              if (hasAnswered) {
                return (
                  <Box mt={6} display="flex" justifyContent="center">
                    <Typography
                      variant="h6"
                      color="text.primary"
                      align="center"
                    >
                      Your answers has been sent! ✈️
                      <Typography
                        variant="body1"
                        color="text.secondary"
                        align="center"
                      >
                        Waiting for company evaluation...
                      </Typography>
                    </Typography>
                  </Box>
                );
              } else {
                return (
                  <Box mt={6} display="flex" justifyContent="center">
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() => {
                        clickSendAnswers();
                      }}
                    >
                      Send Interview
                    </Button>
                  </Box>
                );
              }
            }
          })(hasAnswered)}
        </Box>
      </Box>
    </Box>
  );
};
