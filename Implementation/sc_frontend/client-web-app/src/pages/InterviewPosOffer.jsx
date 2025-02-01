import React from "react";
import { Outlet } from "react-router-dom";
import { IntPosOfferProvider } from "../components/IntPosOffer/IntPosOfferContext";
import { useGlobalContext } from "../global/GlobalContext";

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
