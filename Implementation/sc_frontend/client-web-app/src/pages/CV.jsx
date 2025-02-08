import React from "react";
import SCCv from "../components/CV/SCCV";
import { Outlet } from "react-router-dom";
import { CVProvider } from "../components/CV/CVContext";

const CV = () => {
  return (
    <CVProvider>
      <Outlet />
    </CVProvider>
  );
};

export default CV;
