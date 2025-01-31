import React from "react";
import { Outlet } from "react-router-dom";
import { CommunicationsProvider } from "../components/Communications/CommunicationsContext";

const Communications = () => {
  return (
    <CommunicationsProvider>
      <Outlet />
    </CommunicationsProvider>
  );
};

export default Communications;
