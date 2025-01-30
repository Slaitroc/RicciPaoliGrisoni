import React from "react";
import { InterviewProvider } from "../components/Interviews/InterviewContext";
import { Outlet } from "react-router-dom";

const Interviews = () => {
  return (
    <InterviewProvider>
      <Outlet />
    </InterviewProvider>
  );
};

export default Interviews;
