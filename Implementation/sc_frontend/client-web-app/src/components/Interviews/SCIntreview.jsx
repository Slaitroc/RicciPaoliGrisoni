import React, { useEffect, useState } from "react";
import {
  Avatar,
  Badge,
  Box,
  Button,
  IconButton,
  badgeClasses,
} from "@mui/material";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";
import { useGlobalContext } from "../../global/GlobalContext";
import { useNavigate, useParams } from "react-router-dom";
import { useInterviewsContext } from "./InterviewsContext";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

import * as logger from "../../logger/logger";
import * as interview from "../../api-calls/api-wrappers/Interview/interview";
import {
  COMPANY_USER_TYPE,
  STUDENT_USER_TYPE,
} from "../../global/globalStatesInit";

export default function SCInterview() {
  const navigate = useNavigate();
  const { id } = useParams();
  const { interviewObject: interviewObject } = useInterviewsContext();
  const { userType, setLoading } = useGlobalContext();

  const buildButton = (label, navigateTo) => {
    return (
      <Button
        variant="outlined"
        onClick={() => {
          navigate(navigateTo);
        }}
        align="left"
        sx={{
          whiteSpace: "nowrap",
          width: "50%",
          paddingX: 8,
        }}
      >
        {label}
      </Button>
    );
  };

  const clickBackToPreview = async () => {
    navigate(`/dashboard/interviews`);
  };

  const returnButton = async (status, hasAnswered) => {
    if (userType === STUDENT_USER_TYPE) {
      if (status === "toBeSubmitted") {
        return; //se in toBeSubmitted non può fare nulla quindi non ritorna il button
      } else if (status === "submitted" && hasAnswered === false) {
        return buildButton(
          "Answer Interview",
          `/dashboard/interviews/answer/${id}`
        );
      } else if (status === "submitted" && hasAnswered === true) {
        return buildButton("Show Answers", `/dashboard/interviews/check/${id}`);
      } else if (status === "passed" || status === "failed") {
        return buildButton(
          "Show Evaluation",
          `/dashboard/interviews/check/${id}`
        );
      }
    } else if (userType === COMPANY_USER_TYPE) {
      if (status === "toBeSubmitted") {
        return buildButton(
          "Create Interview",
          `/dashboard/interviews/check/${id}`
        );
      } else if (status == "submitted" && hasAnswered === true) {
        return buildButton(
          "Evaluate Interview",
          `/dashboard/interviews/check/${id}`
        );
      } else if (status == "submitted" && hasAnswered === false) {
        return buildButton(
          "Show Questions",
          `/dashboard/interviews/check/${id}`
        );
      } else if (status == "passed" || status == "failed") {
        return buildButton(
          "Show Evaluation",
          `/dashboard/interviews/check/${id}`
        );
      }
    } else return "ciao"; //TODO alert?;
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
            <Box
              display="flex"
              paddingBottom={2}
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
                <IconButton size="small" onClick={clickBackToPreview}>
                  <ArrowBackIcon />
                </IconButton>
              </Badge>
            </Box>
            <Box display="flex" flexDirection="column" alignItems="end">
              {interviewObject &&
                returnButton(
                  interviewObject.status.value,
                  interviewObject.hasAnswered.value
                )}
            </Box>
          </Box>
          <Box
            display="flex"
            flexDirection="row"
            gap={2}
            alignItems="middle"
            alignContent="center"
          >
            <Box display="flex" flexDirection="column" gap={2}>
              <Box gap={1} display="flex" flexDirection="row" paddingBottom={3}>
                <Typography variant="h2" gutterBottom>
                  Interview {`ID: ` + id + ` `}
                </Typography>
              </Box>
              {interviewObject &&
                Object.entries(interviewObject).map((field) => {
                  if (
                    field[0] === "companyID" ||
                    field[0] === "companyName" ||
                    field[0] === "id" ||
                    field[0] === "status"
                  )
                    return null;
                  return (
                    <Box key={uuidv4()} id={field.id} sx={{ mb: 2 }}>
                      <Box display="flex" flexDirection="column" gap={1}>
                        <Typography variant="h6">{field[1].label}:</Typography>
                        <Typography
                          variant="body1"
                          whiteSpace="pre-line"
                          color="text.secondary"
                        >
                          {((value) => {
                            if (value && value !== 0) {
                              return value;
                            } else {
                              return "No content provided.";
                            }
                          })(field[1].value) && field[1].value}
                        </Typography>
                      </Box>
                    </Box>
                  );
                })}
            </Box>

            <Box
              display="flex"
              flexDirection="column"
              alignItems="center"
              sx={{ flexGrow: 1 }}
            >
              <Box>
                <Typography variant="h6">
                  {interviewObject.status.label}:
                </Typography>
                <Typography
                  variant="body1"
                  whiteSpace="pre-line"
                  color="text.secondary"
                >
                  {((value) => {
                    let content = false;
                    if (value && value !== 0) {
                      content = value;
                    } else {
                      content = "No content provided.";
                    }
                    if (content) {
                      if (content === "toBeSubmitted") return "TO BE SUBMITTED";
                      else if (content === "submitted") return "SUBMITTED";
                      else if (content === "failed") return "FAILED";
                      else if (content === "passed") return "PASSED";
                      else return content;
                    }
                  })(interviewObject?.status.value)}
                </Typography>
              </Box>
              <Box padding={5} justifyContent="center">
                <Typography variant="h6" align="center">
                  AnsweredByStudent:
                  <Typography
                    variant="body1"
                    color="text.secondary"
                    align="center"
                  >
                    {interviewObject.hasAnswered ? "Yes" : "No"}
                  </Typography>
                </Typography>
              </Box>
            </Box>
          </Box>
        </Box>
      </Box>
    </>
  );
}
