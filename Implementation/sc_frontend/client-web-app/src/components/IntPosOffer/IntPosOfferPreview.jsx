import React from "react";
import { Grid2, Card, CardContent, Typography } from "@mui/material";
import * as logger from "../../logger/logger";
import { useIntPosOfferContext } from "./IntPosOfferContext";

export const IntPosOfferPreview = () => {
  const { intPosOfferData, clickOnIntPosOffer } = useIntPosOfferContext();
  logger.debug("intPosOfferData: ", intPosOfferData);
  return (
    <>
      {intPosOfferData != null ? (
        <Grid2 padding={5} container spacing={3}>
          {intPosOfferData.map((item) => {
            logger.debug("item: ", item);
            return (
              <Grid2 key={item.id} xs={12} sm={6} md={4}>
                <Card
                  onClick={() => clickOnIntPosOffer(item)}
                  sx={{
                    height: "auto",
                    width: 500,
                    display: "flex",
                    flexDirection: "column",
                    "&:hover, &:focus-visible": {
                      backgroundColor: "rgba(255, 255, 255, 0.8)",
                      cursor: "pointer",
                      ".MuiTypography-body1": {
                        color: "gray",
                      },
                      ".MuiTypography-body2, .MuiTypography-h5": {
                        color: "Black",
                      },
                      // Target status text specifically on hover
                      ".status-text .MuiTypography-body1": {
                        color: (theme) =>
                          item.status === "accepted"
                            ? theme.palette.success.main
                            : item.status === "rejected"
                            ? theme.palette.error.main
                            : theme.palette.text.secondary,
                        fontWeight: "bold",
                      },
                      outline: "3px solid",
                      outlineColor:
                        item.status === "accepted"
                          ? "green"
                          : item.status === "rejected"
                          ? "red"
                          : "hsla(210, 98%, 48%, 0.5)",
                      outlineOffset: "2px",
                    },
                  }}
                >
                  <CardContent>
                    <Typography variant="h5" gutterBottom color="text.primary">
                      {item.internshipTitle}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
                      >
                        Company:
                      </Typography>
                      {" " + item.companyName}
                    </Typography>
                    {/* Wrapped status text for specific styling */}
                    <div className="status-text">
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
                    </div>
                    <Typography variant="body1" color="text.secondary">
                      <Typography
                        component="span"
                        display="inline"
                        variant="body2"
                        sx={{ color: "text.primary" }}
                      >
                        Interview ID:
                      </Typography>
                      {" " + item.interviewID}
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

export default IntPosOfferPreview;
