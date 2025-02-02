import React from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import * as logger from "../../logger/logger";
import InterviewTemplate from "./InterviewTemplate";
import { Divider } from "@mui/material";
import { Grid2, Card, CardContent, Typography } from "@mui/material";
import { useInterviewTemplateContext } from "./InterviewTemplateContext";

// Main component
export const InterviewTemplatePreview = () => {
  const { profile } = useGlobalContext();
  const [selectedItem, setSelectedItem] = React.useState(null);
  const {
    interviewTemplateData,
    setOpenAlert,
    openAlert,
    setAlertMessage,
    setAlertSeverity,
  } = useInterviewTemplateContext();

  //appare solo una volta poi si frezza tutto idk why
  const onError = (response) => {
    setOpenAlert(true);
    setAlertMessage(response.message);
    setAlertSeverity(response.severity);
  };

  const handleClick = (item) => {
    //setOpenAlert(close);
    setSelectedItem(item);
  };

  const handleClose = () => {
    setSelectedItem(null);
  };

  return (
    <>
      {selectedItem && (
        <InterviewTemplate
          item={selectedItem}
          onError={onError}
          onClose={handleClose}
        />
      )}
      {interviewTemplateData && (
        <Grid2 padding={5} container spacing={3}>
          {interviewTemplateData.map((item) => {
            return (
              <InterviewTemplateCard
                key={item.id}
                item={item}
                onClick={() => handleClick(item)}
              ></InterviewTemplateCard>
            );
          })}
        </Grid2>
      )}
    </>
  );
};

const InterviewTemplateCard = ({ item, onClick }) => {
  return (
    <Card
      onClick={() => onClick(item)}
      sx={{
        height: "auto",
        maxHeight: 300,
        width: 500,
        display: "flex",
        flexDirection: "column",
        overflow: "auto",
        "&:hover, &:focus-visible": {
          backgroundColor: "rgba(255, 255, 255, 0.8)",
          cursor: "pointer",
          ".MuiTypography-body1": {
            color: "gray",
          },
          ".MuiTypography-body2, .MuiTypography-h5": {
            color: "Black",
          },
        },
      }}
    >
      <CardContent>
        <Typography
          variant="h5"
          gutterBottom
          color="text.primary"
          align="center"
        >
          {"Interview Template ID: " + item.id.value}
        </Typography>
        <Divider variant="middle" sx={{ my: 1 }} />
        {renderDetail("Question 1", item.question1.value)}
        {renderDetail("Question 2", item.question2.value)}
        {renderDetail("Question 3", item.question3.value)}
        {renderDetail("Question 4", item.question4.value)}
        {renderDetail("Question 5", item.question5.value)}
        {renderDetail("Question 6", item.question6.value)}
      </CardContent>
    </Card>
  );
};

const renderDetail = (label, value) => {
  return (
    <Typography variant="body1" color="text.secondary">
      <Typography
        component="span"
        display="inline"
        variant="body2"
        sx={{ color: "text.primary" }}
      >
        {label}:
      </Typography>
      {" " + value}
    </Typography>
  );
};

export default InterviewTemplatePreview;
