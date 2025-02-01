import React from "react";
import { InterviewsProvider } from "../components/Interviews/InterviewsContext";
import { Outlet } from "react-router-dom";

const Interviews = () => {
  return (
    <InterviewsProvider>
      <Outlet />
    </InterviewsProvider>
  );
};

export default Interviews;
