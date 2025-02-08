import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import { ApplicationsProvider } from "../components/Applications/ApplicationsContext";

const Applications = () => {
  return (
    <ApplicationsProvider>
      <Outlet />
    </ApplicationsProvider>
  );
};

export default Applications;
