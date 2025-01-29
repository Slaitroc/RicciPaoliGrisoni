import React from "react";
import { Grid2, Card, CardContent, Typography } from "@mui/material";
import { useApplicationContext } from "./ApplicationsContext";

export const SCApplication = () => {
  const { applicationData } = useApplicationContext();

  console.log("Application data:", applicationData);
  return (
    <>
      {applicationData != null ? (
        <Grid2 padding={5} container spacing={3}>
          {applicationData.map((item) => {
            return (
              <Grid2 key={item.id} xs={12} sm={6} md={4}>
                <Card
                  onClick={() => buttonAction(item)} // Added click handler
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
                      {item.internshipOfferTitle}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
                      >
                        Student Name:
                      </Typography>
                      {" " + item.studentName}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
                      >
                        Company Name:
                      </Typography>
                      {" " + item.internshipOfferCompanyName}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
                      >
                        Status:
                      </Typography>
                      {" " + item.status}
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

export default SCApplication;
