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
  FormControl,
  FormLabel,
  TextField,
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
  const [openTipAlert, setOpenTipAlert] = useState(true);
  const { interviewObject, setInterviewObject, openAlertProc } =
    useInterviewsContext();

  const interviewObjectFetch = async () => {
    const response = await interview.getFormattedInterview(id);
    if (response.success === true) {
      setInterviewObject(response.data);
    } else {
      openAlertProc("Failed to fetch interview", "error");
    }
  };

  const questionFetch = async () => {
    if (interviewObject?.interviewTemplateID?.value) {
      try {
        const response = await interview.getFormattedInterviewTemplateQuestions(
          interviewObject.interviewTemplateID?.value
        );
        if (response.success === true) {
          setQuestions(response.data);
        } else {
          openAlertProc("Failed to fetch questions", "error");
        }
      } catch (error) {
        throw error;
      }
    }
  };

  const answerFetch = async () => {
    if (interviewObject.interviewQuizID?.value) {
      try {
        const response = await interview.getFormattedStudentAnswers(
          interviewObject.interviewQuizID?.value
        );
        if (response.success === true) {
          setAnswers(response.data);
        } else {
          openAlertProc("No answer fetched", "warning");
        }
      } catch (error) {
        throw error;
      }
    }
  };

  const sendAnswer = async () => {
    try {
      const response = interview.sendInterviewAnswers(id, answerRef.current);
      if (response.success === true) {
        setAnswers(response.data);
      } else {
        openAlertProc("Failed to send answers", "error");
      }
    } catch (e) {
      throw error;
    }
  };

  const evaluateInterview = (id, evaluation) => {
    try {
      logger.focus("evaluation", evaluationRef.current);
      const response = interview.sendEvaluation(id, evaluation);
      if (response.success === true) {
        setInterviewObject(response.data);
      } else {
        openAlertProc("Failed to evaluate interview", "error");
      }
    } catch (error) {
      throw error;
    }
  };

  useEffect(() => {
    interviewObjectFetch();
  }, []);
  useEffect(() => {
    questionFetch();
    answerFetch();
  }, [interviewObject]);

  const evaluationRef = useRef(0);
  const questionsRef = useRef({
    question6: "",
    question3: "",
    question2: "",
    question5: "",
    question4: "",
    question1: "",
  });

  const answerRef = useRef({
    answer6: "",
    answer3: "",
    answer2: "",
    answer5: "",
    answer4: "",
    answer1: "",
  });

  const questionLabels = {
    question2: "Question 2",
    question1: "Question 1",
    question3: "Question 3",
    question4: "Question 4",
    question5: "Question 5",
    question6: "Question 6",
  };

  const answerLabels = {
    answer2: "Answer 2",
    answer1: "Answer 1",
    answer3: "Answer 3",
    answer4: "Answer 4",
    answer5: "Answer 5",
    answer6: "Answer 6",
  };

  const mapQuestionAnswer = {
    question1: "answer1",
    question2: "answer2",
    question3: "answer3",
    question4: "answer4",
    question5: "answer5",
    question6: "answer6",
  };

  const sendInterview = async () => {
    try {
      const response = await interview.sendInterviewQuestions(
        id,
        questionsRef.current
      );
      setQuestions(questionsRef.current);
      if (response.success === true) {
        setInterviewObject(response.data);
      } else {
        logger.error("Failed to send interview", response.error);
      }
    } catch (error) {
      throw error;
    }
  };

  const [questions, setQuestions] = useState(questionsRef.current);
  const [answers, setAnswers] = useState(answerRef.current);

  const handleFieldChange = (toUpdate, updateValue) => {
    logger.focus("aggiorna", toUpdate, updateValue);
    questionsRef.current[toUpdate] = updateValue;
    logger.focus("aggiorna", questionsRef.current);
  };

  const handleFieldAnswerChange = (toUpdate, updateValue) => {
    logger.focus("aggiorna", toUpdate, updateValue);
    answerRef.current[toUpdate] = updateValue;
    logger.focus("aggiorna", questionsRef.current);
  };

  const clickBackToDetails = () => {
    navigate(`/dashboard/interviews/details/${id}`);
  };

  const returnGridElement = (status, hasAnswered) => {
    if (userType === STUDENT_USER_TYPE) {
      if (status === "toBeSubmitted") {
        return;
      } else if (status === "submitted" && hasAnswered === false) {
        return (
          <>
            <Grid
              container
              spacing={3}
              display={{ xs: "block", sm: "flex" }}
              columns={12}
              alignItems="stretch"
            >
              {Object.entries(questions).map(([key, value], index) => {
                if (key !== "id") {
                  return (
                    <Grid
                      item="true"
                      size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                      key={key}
                    >
                      <Box display="flex">
                        <FormControl sx={{ flexGrow: 1 }}>
                          <FormLabel>{value}</FormLabel>
                          <TextField
                            multiline
                            variant="outlined"
                            placeholder={"Type your answer..."}
                            id={`${key}`}
                            onBlur={
                              (e) =>
                                handleFieldAnswerChange(
                                  mapQuestionAnswer.value,
                                  e.target.value
                                ) //DANGER
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
                } else return null;
              })}
            </Grid>
          </>
        );
      } else if (status === "passed" || status === "failed") {
        return (
          <>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  1. {questions?.question1}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer1}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer2}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  2. {questions?.question2}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer2}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer3}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  3. {questions?.question3}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer3}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer4}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  4. {questions?.question4}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer4}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer5}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  5. {questions?.question5}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer5}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer6}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  6. {questions?.question6}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer6}
                </Typography>
              </Box>
            </Grid>
          </>
        );
      } // TODO
      else if (status === "submitted" && hasAnswered === true) {
        return (
          <>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers.answer1}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  1. {questions?.question1}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer1}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer2}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  2. {questions?.question2}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer2}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer3}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  3. {questions?.question3}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer3}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer4}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  4. {questions?.question4}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer4}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer5}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  5. {questions?.question5}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer5}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer6}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  6. {questions?.question6}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer6}
                </Typography>
              </Box>
            </Grid>
          </>
        );
      }
    } else if (userType === COMPANY_USER_TYPE) {
      if (status === "toBeSubmitted") {
        return (
          <>
            <Grid
              container
              spacing={3}
              display={{ xs: "block", sm: "flex" }}
              columns={12}
              alignItems="stretch"
            >
              {Object.entries(questions).map(([key, value], index) => {
                if (key !== "id") {
                  return (
                    <Grid
                      item="true"
                      size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                      key={key}
                    >
                      <Box display="flex">
                        <FormControl sx={{ flexGrow: 1 }}>
                          <FormLabel>Question {index + 1}</FormLabel>
                          <TextField
                            multiline
                            variant="outlined"
                            placeholder={"Type your question"}
                            id={`${key}`}
                            onBlur={(e) =>
                              handleFieldChange(key, e.target.value)
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
                } else return null;
              })}
            </Grid>
          </>
        );
      } else if (status === "submitted" && hasAnswered === true) {
        return (
          <>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer1}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  1. {questions?.question1}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer1}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer2}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  2. {questions?.question2}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer2}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer3}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  3. {questions?.question3}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer3}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer4}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  4. {questions?.question4}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer4}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer5}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  5. {questions?.question5}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer5}
                </Typography>
              </Box>
            </Grid>
            <Grid
              item="true"
              size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
              key={answers?.answer6}
            >
              <Box display="flex" flexDirection="column">
                <Typography variant="h6" color="text.primary">
                  6. {questions?.question6}
                </Typography>
                <Typography variant="h6" color="text.secondary">
                  {interviewObject.hasAnswered?.value && answers?.answer6}
                </Typography>
              </Box>
            </Grid>
          </>
        );
      } // TODO
      else if (status === "submitted" && hasAnswered === false) {
        return (
          <>
            <Grid
              container
              spacing={12}
              display={{ xs: "block", sm: "flex" }}
              columns={12}
              sx={{ flexGrow: 1 }}
            >
              {Object.entries(questions).map(([key, value], index) => {
                if (key !== "id") {
                  return (
                    <Grid
                      item="true"
                      size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                      key={key}
                    >
                      <Box display="flex" alignItems="center" flexGrow={1}>
                        <FormControl sx={{ flexGrow: 1 }}>
                          <Typography
                            align="center"
                            variant="body1"
                            color="text.primary"
                          >
                            {questionLabels[key] || key}
                          </Typography>
                          <Typography
                            align="center"
                            variant="body1"
                            color="text.primary"
                          >
                            {value?.value || value}
                          </Typography>
                        </FormControl>
                      </Box>
                    </Grid>
                  );
                } else return null;
              })}
            </Grid>
          </>
        );
      }
    }
  };

  const returnAlertTip = (status, hasAnswered) => {
    if (userType === STUDENT_USER_TYPE) {
      //page is unreachable in this state
      if (status === "toBeSubmitted") return;
      //page is unreachable in this state
      else if (status === "submitted" && hasAnswered === false) return;
      else if (status === "passed" || status === "failed")
        return (
          <Alert
            severity="info"
            sx={{ mb: 2 }}
            onClose={() => setOpenTipAlert(false)}
          >
            <Typography variant="h6" inline="true">
              Interview Evaluated!
              <Typography inline="true" variant="body1" color="text.secondary">
                See the outcome in the interview details.
              </Typography>
            </Typography>
            <Typography variant="h6" inline="true">
              Note:
              <Typography inline="true" variant="body1" color="text.secondary">
                The evaluation is final and cannot be changed.
              </Typography>
            </Typography>
          </Alert>
        );
      else if (status === "submitted" && hasAnswered === true)
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
              <Typography inline="true" variant="body1" color="text.secondary">
                Your answers cannot be changed.
              </Typography>
            </Typography>
          </Alert>
        );
    } else if (userType === COMPANY_USER_TYPE) {
      //page is unreachable in this state
      if (status === "toBeSubmitted") {
        logger.focus("CIAOOO", openTipAlert, status);

        return (
          <>
            {openTipAlert && (
              <Alert
                severity="info"
                sx={{ mb: 2 }}
                onClose={() => setOpenTipAlert(false)}
              >
                <Typography variant="body1">
                  Create your Internship Offer and click Send Internship to send
                  it to the world!
                </Typography>
                <Typography variant="h6" inline="true">
                  Remember:
                  <Typography inline="true" variant="body1">
                    Using keywords instead of long phrases increases the chances
                    of getting a match in the recommendation process.
                  </Typography>
                </Typography>
              </Alert>
            )}
          </>
        );
      } else if (status === "submitted" && hasAnswered === true)
        return (
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
        );
      else if (status === "submitted" && hasAnswered === false)
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
              <Typography inline="true" variant="body1" color="text.secondary">
                Answers and evaluation are final and cannot be changed.
              </Typography>
            </Typography>
          </Alert>
        );
    }
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
            interviewObject.status?.value,
            interviewObject.hasAnswered?.value
          )}
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {returnGridElement(
            interviewObject.status?.value,
            interviewObject.hasAnswered?.value
          )}
        </Grid>
        <Box display="flex" justifyContent="center">
          {userType === COMPANY_USER_TYPE && (
            <>
              {interviewObject.status?.value === "submitted" &&
              interviewObject.hasAnswered?.value ? (
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
                    onClick={() => evaluateInterview(id, evaluationRef.current)}
                  >
                    Send Evaluation
                  </Button>
                </Box>
              ) : (
                !interviewObject.hasAnswered?.value &&
                interviewObject.status?.value === "submitted" && (
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
                )
              )}
            </>
          )}
          {userType === COMPANY_USER_TYPE &&
            interviewObject.status?.value === "toBeSubmitted" &&
            !interviewObject.hasAnswered?.value && (
              <>
                <Box
                  mt={6}
                  display="flex"
                  justifyContent="center"
                  flexDirection="column"
                  gap={3}
                >
                  {" "}
                  <Typography
                    variant="body1"
                    color="text.primary"
                    align="center"
                  >
                    Fill the data and...
                  </Typography>
                  <Button
                    variant="outlined"
                    align="center"
                    onClick={sendInterview}
                    sx={{ height: 70 }}
                  >
                    <Typography
                      variant="h4"
                      color="text.primary"
                      align="center"
                    >
                      Send Interview ✈️
                    </Typography>
                  </Button>
                </Box>
              </>
            )}
          {userType === STUDENT_USER_TYPE && (
            <>
              {interviewObject.hasAnswered?.value
                ? interviewObject.status?.value === "submitted" && (
                    <Box mt={6} display="flex" justifyContent="center">
                      <Typography
                        variant="h6"
                        color="text.primary"
                        align="center"
                      >
                        Your answers have been sent! ✈️
                        <Typography
                          variant="body1"
                          color="text.secondary"
                          align="center"
                        >
                          Waiting for company evaluation...
                        </Typography>
                      </Typography>
                    </Box>
                  )
                : interviewObject.status?.value === "submitted" && (
                    <Box mt={6} display="flex" justifyContent="center">
                      <Button
                        variant="outlined"
                        align="center"
                        onClick={sendAnswer}
                        sx={{ height: 70 }}
                      >
                        <Typography
                          variant="h4"
                          color="text.primary"
                          align="center"
                        >
                          Send Answers ✈️
                        </Typography>
                      </Button>
                    </Box>
                  )}
            </>
          )}
        </Box>
      </Box>
    </Box>
  );
};
