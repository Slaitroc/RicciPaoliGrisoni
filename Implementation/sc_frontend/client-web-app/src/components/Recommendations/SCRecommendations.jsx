import * as React from "react";
import * as logger from "../../logger/logger";
import { useRecommendationsContext } from "./RecommendationsContext";
import AnimatedCard from "./Card/AnimatedCard";
import { Button, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";
import LastCard from "./Card/LastCard";
export default function SCRecommendations() {
  const { recommendationOfferList, profile } = useRecommendationsContext();

  const navigate = useNavigate();
  const handleErrorButtonClick = () => {
    profile.userType === "STUDENT"
      ? navigate("/dashboard/browse-internship-offers")
      : navigate("/dashboard/internship-offers");
  };

  const [isHiddenArray, setIsHiddenArray] = React.useState([]);

  const updateIsHiddenArray = (index) => {
    setIsHiddenArray((prev) => {
      const newArray = [...prev];
      newArray[index] = true;
      return newArray;
    });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      width="100%"
      padding={2}
      sx={{ boxSizing: "border-box", minHeight: "80vh" }}
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
        <div style={{ position: "absolute", zIndex: 0 }}>
          <LastCard profile={profile} />
        </div>
        {recommendationOfferList.map((item, index) => (
          <AnimatedCard
            key={item.recommendation.id}
            item={item}
            index={index}
            isHidden={isHiddenArray[index]}
            removeCard={updateIsHiddenArray}
            style={{ position: "absolute" }}
          />
        ))}
      </div>

      <Box sx={{ display: "flex", justifyContent: "center", width: "100%" }}>
        <Button variant="outlined" onClick={handleErrorButtonClick}>
          {profile.userType === "STUDENT"
            ? "Browse All Internship Offers!"
            : "See your Offer!"}
        </Button>
      </Box>
    </Box>
  );
}
