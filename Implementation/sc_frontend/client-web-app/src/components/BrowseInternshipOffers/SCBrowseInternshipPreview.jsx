import React from "react";
import { useBrowseInternshipContext } from "./BrowseInternshipContext";
import {
  Grid2,
  Card,
  CardContent,
  Typography,
  Box,
  IconButton,
  Button,
} from "@mui/material";

export const SCBrowseInternshipPreview = () => {
  const { offerData, clickOnOffer } = useBrowseInternshipContext();
  console.log("Offer Data:", offerData);
  return (
    <>
      {offerData && (
        <Grid2 padding={5} container spacing={3}>
          {offerData.map((item) => {
            return (
              <Grid2
                item
                key={item.id}
                size={{ xs: 12, sm: 12, md: 12, lg: 6, xl: 4 }}
                display="flex"
              >
                <Card
                  onClick={() => clickOnOffer(item)} // Added click handler
                  sx={{
                    height: "auto",
                    width: "100%",
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
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Typography variant="h5" gutterBottom color="text.primary">
                      {item.title}
                    </Typography>
                    <Typography
                      variant="body1"
                      sx={{
                        color: "text.secondary",
                        wordWrap: "break-word",
                        overflowWrap: "break-word",
                      }}
                    >
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{
                          color: "text.primary",
                          wordWrap: "break-word",
                          overflowWrap: "break-word",
                        }}
                      >
                        Description:
                      </Typography>
                      {" " + item.description}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{
                          color: "text.primary",
                          wordWrap: "break-word",
                          overflowWrap: "break-word",
                        }}
                      >
                        Skill Required:
                      </Typography>
                      {" " + item.requiredSkills}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{
                          color: "text.primary",
                          wordWrap: "break-word",
                          overflowWrap: "break-word",
                        }}
                      >
                        Compensation:
                      </Typography>
                      {" " + item.compensation}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{
                          color: "text.primary",
                          wordWrap: "break-word",
                          overflowWrap: "break-word",
                        }}
                      >
                        Duration in hours:
                      </Typography>
                      {" " + item.duration}
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

export default SCBrowseInternshipPreview;
