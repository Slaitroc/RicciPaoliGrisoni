import React from "react";
import {
  Grid2,
  Card,
  CardContent,
  Typography,
  Box,
  IconButton,
  Button,
} from "@mui/material";
import { SCAddIcon } from "../Shared/SCIcons";
import { useInterviewsContext } from "./InterviewsContext";
import * as logger from "../../logger/logger";
import { useNavigate } from "react-router-dom";

export const SCInterviewsPreview = () => {
  const navigate = useNavigate();
  const { interviewsArray, reloadSnapshot, clickOfferPreview } =
    useInterviewsContext();

  return (
    <>
      <div style={{ margin: "20px 0" }}></div>
      {interviewsArray && (
        <Grid2 padding={5} container spacing={3}>
          {interviewsArray.map((item) => {
            return (
              <Grid2 item="true" key={item.id.value} xs={12} sm={6} md={4}>
                <Card
                  onClick={() => clickOfferPreview(item.id.value)}
                  sx={{
                    height: "auto",
                    width: 500,
                    display: "flex",
                    flexDirection: "column",
                    "&:hover, &:focus-visible": {
                      backgroundColor: "rgba(255, 255, 255, 0.8)", // Lower opacity white background
                      cursor: "pointer",
                      ".MuiTypography-body1": {
                        color: "gray", // Change text color to black on hover/focus
                      },
                      ".MuiTypography-body2, .MuiTypography-h5": {
                        color: "Black",
                      },
                      outline: "3px solid",
                      outlineColor: "hsla(210, 98%, 48%, 0.5)",
                      outlineOffset: "2px",
                    },
                  }}
                >
                  <CardContent>
                    <Typography variant="h5" gutterBottom color="text.primary">
                      {`ID ` + item.id.value}
                    </Typography>
                    {Object.entries(item).map((field) => {
                      //NOTE field filter
                      if (
                        field[0] === "id" ||
                        field[0] === "hasAnswered" ||
                        field[0] === "studentID" ||
                        field[0] === "companyID" ||
                        field[0] === "interviewQuizID" ||
                        field[0] === "interviewTemplateID"
                      )
                        return null;
                      return (
                        <Typography
                          key={field[0]}
                          variant="body1"
                          color="text.secondary"
                        >
                          <Typography
                            component="span"
                            display="inline"
                            variant="body2"
                            sx={{ color: "text.primary" }}
                          >
                            {field[1].label}:
                          </Typography>
                          {" " +
                            ((value) => {
                              if (value) {
                                if (value === "toBeSubmitted")
                                  return "TO BE SUBMITTED";
                                else if (value === "submitted")
                                  return "SUBMITTED";
                                else if (value === "failed") return "FAILED";
                                else if (value === "passed") return "PASSED";
                                else return value;
                              } else return "No Content";
                            })(field[1].value)}
                        </Typography>
                      );
                    })}
                  </CardContent>
                </Card>
              </Grid2>
            );
          })}
        </Grid2>
      )}
    </>
  );
};
