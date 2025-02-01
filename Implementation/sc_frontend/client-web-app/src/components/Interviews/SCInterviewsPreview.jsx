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
  const { offersArray, reloadSnapshot } = useInterviewsContext();

  const handleOfferClick = (id) => {
    console.log("Selected Offer:", id);
    reloadSnapshot(id);
    //NAV to internship detail
    navigate(`/dashboard/internship-offers/details/${id}`);
  };

  return (
    <>
      <div style={{ margin: "20px 0" }}></div>
      <div style={{ display: "flex", justifyContent: "center" }}>
        <Box paddingLeft={5}>
          <Button startIcon={<SCAddIcon />} variant="outlined">
            Create New Internship Offer
          </Button>
        </Box>
      </div>
      {offersArray && (
        <Grid2 padding={5} container spacing={3}>
          {offersArray.map((item) => {
            return (
              <Grid2 item="true" key={item.id.value} xs={12} sm={6} md={4}>
                <Card
                  onClick={() => handleOfferClick(item.id.value)}
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
                      {item.title.value}
                    </Typography>
                    {Object.entries(item).map((field) => {
                      if (
                        field[0] === "id" ||
                        field[0] === "title" ||
                        field[0] === "startDate" ||
                        field[0] === "endDate" ||
                        field[0] === "companyName" ||
                        field[0] === "numberPositions" ||
                        field[0] === "location" ||
                        field[0] === "companyID" ||
                        field[0] === "duration"
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
                          {" " + field[1].value}
                        </Typography>
                      );
                    })}
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
                      >
                        {item.duration.label}:
                      </Typography>
                      {" " + item.duration.value}
                    </Typography>
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

export default SCInterviewsPreview;
