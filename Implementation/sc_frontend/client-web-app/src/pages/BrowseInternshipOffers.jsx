import React from "react";
import { Outlet } from "react-router-dom";
import { BrowseInternshipOffersProvider } from "../components/BrowseInternshipOffers/BrowseInternshipContext";

const BrowseInternshipOffers = () => {
  return (
    <BrowseInternshipOffersProvider>
      <Outlet />
    </BrowseInternshipOffersProvider>
  );
};

export default BrowseInternshipOffers;
