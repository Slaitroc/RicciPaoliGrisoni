import * as React from "react";
import * as logger from "../../logger/logger";
import { useRecommendationsContext } from "./RecommendationsContext";
import RecommendationCard from "./RecommendationCard";
import { Grid2 } from "@mui/material";
import { useMotionValue, useTransform, motion, animate } from "framer-motion";
import { Button, Box } from "@mui/material";
export default function SCRecommendations() {
  const {
    recommendationOfferList,
    handleDragEnd,
    profile,
    handleErrorButtonClick,
  } = useRecommendationsContext();

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      width="100%"
      padding={2}
      sx={{
        boxSizing: "border-box",
        minHeight: "80vh",
      }}
    >
      {/* Card Container */}
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          flex: 1,
          position: "relative",
          width: "100%",
          maxWidth: 500,
        }}
      >
        {recommendationOfferList.map((recommendationOffer) => (
          <motion.div
            key={recommendationOffer.recommendation.id}
            style={{
              position: "absolute",
              width: "100%",
              height: "100%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
            drag="x"
            dragConstraints={{ left: 0, right: 0, top: 0, bottom: 0 }}
            onDragEnd={handleDragEnd}
          >
            <RecommendationCard
              recommendation={recommendationOffer.recommendation}
              offer={recommendationOffer.offer}
              sx={{ width: "100%", height: "100%" }}
            />
          </motion.div>
        ))}
      </div>

      {profile.userType === "STUDENT" && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            width: "100%",
          }}
        >
          <Button variant="outlined" onClick={handleErrorButtonClick}>
            Browse All Internship Offers!
          </Button>
        </Box>
      )}
    </Box>
  );
}
