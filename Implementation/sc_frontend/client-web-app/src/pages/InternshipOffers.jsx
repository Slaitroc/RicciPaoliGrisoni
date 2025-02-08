import React, { useEffect, useState } from "react";
import SCIntOffersPreview from "../components/InternshipOffers/SCIntOffersPreview";
import { useGlobalContext } from "../global/GlobalContext";
import { Alert } from "@mui/material";
import Card from "@mui/material/Card";

import { Outlet } from "react-router-dom";
import { InternshipOffersProvider } from "../components/InternshipOffers/InternshipOffersContext";

const InternshipOffers = () => {
  return (
    <InternshipOffersProvider>
      <Outlet />
    </InternshipOffersProvider>
  );
};

export default InternshipOffers;
