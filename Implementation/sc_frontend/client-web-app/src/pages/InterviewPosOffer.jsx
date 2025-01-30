import React from "react";
import { Outlet } from "react-router-dom";
import { IntPosOfferProvider } from "../components/IntPosOffer/IntPosOfferContext";

const InterviewPosOffer = () => {
  return (
    <>
      <IntPosOfferProvider>
        <Outlet />
      </IntPosOfferProvider>
    </>
  );
};

export default InterviewPosOffer;
