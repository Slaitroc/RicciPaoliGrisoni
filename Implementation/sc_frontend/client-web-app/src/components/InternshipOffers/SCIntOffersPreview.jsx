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
import { useInternshipOffersContext } from "./InternshipOffersContext";

export const SCIntOffersPreview = () => {
  const { offerData } = useInternshipOffersContext();
  return (
    <>
      <Box paddingLeft={5}>
        <Button startIcon={<SCAddIcon />} variant="outlined">
          Create New Internship Offer
        </Button>
      </Box>
      {offerData != null ? (
        <Grid2 padding={5} container spacing={3}>
          {offerData.map((item) => {
            return (
              <Grid2 key={item.id} xs={12} sm={6} md={4}>
                <Card
                  onClick={() => offerClickHandler(item)} // Added click handler
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
                      {item.title}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
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
                        sx={{ color: "text.primary" }}
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
                        sx={{ color: "text.primary" }}
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
                        sx={{ color: "text.primary" }}
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
      ) : null}
    </>
  );
};

export default SCIntOffersPreview;
