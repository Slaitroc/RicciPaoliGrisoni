import React from "react";
import { RecommendationsProvider } from "../components/Recommendations/RecommendationsContext";
import { Outlet } from "react-router-dom";

const Recommendation = () => {
  return (
    <RecommendationsProvider>
      <Outlet />
    </RecommendationsProvider>
  );
};

export default Recommendation;
