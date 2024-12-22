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

export const SCIntOffersPreview = ({ offerData }) => {
  return (
    <>
      <Box paddingLeft={5}>
        <Button startIcon={<SCAddIcon />} variant="outlined">
          Create New Internship Offer
        </Button>
      </Box>
      <Grid2 padding={5} container spacing={3}>
        {offerData.map((item) => {
          return (
            <Grid2 key={item.id} xs={12} sm={6} md={4}>
              <Card
                sx={{
                  height: "auto",
                  width: 500,
                  display: "flex",
                  flexDirection: "column",
                }}
              >
                <CardContent>
                  <Typography variant="h5" gutterBottom color="text.primary">
                    {item.content[0].content[0].content}
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    <Typography
                      display="inline"
                      variant="body2"
                      sx={{ color: "text.primary" }}
                    >
                      Description:
                    </Typography>
                    {item.content[0].content[1].content}
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    <Typography
                      display="inline"
                      variant="body2"
                      sx={{ color: "text.primary" }}
                    >
                      Education Level:
                    </Typography>
                    {item.content[1].content[1].content}
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    <Typography
                      display="inline"
                      variant="body2"
                      sx={{ color: "text.primary" }}
                    >
                      Languages:
                    </Typography>
                    {item.content[1].content[2].content}
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    <Typography
                      display="inline"
                      variant="body2"
                      sx={{ color: "text.primary" }}
                    >
                      Duration:
                    </Typography>
                    {item.content[0].content[2].content}
                  </Typography>
                </CardContent>
              </Card>
            </Grid2>
          );
        })}
      </Grid2>
    </>
  );
};

export default SCIntOffersPreview;
