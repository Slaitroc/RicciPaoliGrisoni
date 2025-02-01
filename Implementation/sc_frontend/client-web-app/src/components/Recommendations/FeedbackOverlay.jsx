import * as React from "react";
import { useState } from "react";
import Rating from "@mui/material/Rating";
import Stack from "@mui/material/Stack";
import { Button, Box, Typography } from "@mui/material";
import * as apiCall from "../../api-calls/apiCalls";
import * as logger from "../../logger/logger";

export default function FeedbackOverlay({
  setFeedbackNeeded,
  onComplete,
  recommendation,
}) {
  const [value, setValue] = useState(3);

  const handleClick = () => {
    logger.debug("Recommendation: ", recommendation);
    const payload = {
      rating: value,
    };
    apiCall.submitFeedback(payload, recommendation.id);
    console.log(value);
    setFeedbackNeeded(false);
    onComplete();
  };

  return (
    <Box
      sx={{
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 9999, // Highest z-index to make sure it's on top (idk if this is a hack or not)
      }}
    >
      {/* Semi-transparent overlay */}
      <Box
        sx={{
          position: "absolute",
          width: "100%",
          height: "100%",
          bgcolor: "rgba(0, 0, 0, 0.5)",
          backdropFilter: "blur(2px)",
        }}
      />

      <Box
        sx={{
          position: "relative",
          bgcolor: "background.paper",
          p: 6,
          borderRadius: 4,
          boxShadow: 24,
          textAlign: "center",
          maxWidth: 670,
          width: "90%",
        }}
      >
        <Stack spacing={3} alignItems="center">
          <Typography
            variant="h3"
            sx={{
              fontWeight: 700,
              letterSpacing: "2px",
              color: "primary.main",
              textTransform: "uppercase",
              mb: 1,
              color: "green",
            }}
          >
            Match Found!
          </Typography>

          <Typography
            variant="subtitle1"
            sx={{
              color: "text.secondary",
              fontSize: "1.2rem",
              mb: 3,
            }}
          >
            Please rate the accuracy of the match
          </Typography>

          <Rating
            name="size-large"
            value={value}
            size="large"
            sx={{ fontSize: "4rem", mb: 3 }}
            onChange={(event, newValue) => setValue(newValue)}
          />

          <Button
            onClick={handleClick}
            variant="contained"
            color="primary"
            size="large"
            sx={{
              px: 6,
              py: 1.5,
              borderRadius: 50,
              fontSize: "1.1rem",
              fontWeight: 700,
              textTransform: "none",
              transition: "all 0.3s ease",
              "&:hover": {
                transform: "translateY(-2px)",
                boxShadow: 3,
              },
            }}
          >
            Submit Rating
          </Button>
        </Stack>
      </Box>
    </Box>
  );
}
