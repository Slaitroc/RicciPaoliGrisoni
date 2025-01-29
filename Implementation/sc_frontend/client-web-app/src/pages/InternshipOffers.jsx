import React, { useEffect, useState } from "react";
import SCIntOffersPreview from "../components/InternshipOffers/SCIntOffersPreview";
import { useGlobalContext } from "../global/GlobalContext";
import { Alert } from "@mui/material";
import Card from "@mui/material/Card";

import { Outlet } from "react-router-dom";
import { InternshipOffersProvider } from "../components/InternshipOffers/InternshipOffersContext";

const InternshipOffers = () => {
  const { profile } = useGlobalContext();
  const [offerData, setOfferData] = useState(null);

  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  //When the component mounts, fetch the internship offers of this company

  return (
    <InternshipOffersProvider>
      <Outlet />
      {/* <Card variant="outlined">
        {openAlert && (
          <>
            <Alert severity={alertSeverity}>{alertMessage}</Alert>
            <div style={{ margin: "20px 0" }}></div>
            <div style={{ display: "flex", justifyContent: "center" }}>
              <SCIntOffersPreview offerData={offerData} />
            </div>
          </>
        )}
        {!openAlert && (
          <SCIntOffersPreview
            offerData={offerData}
            offerClickHandler={handleOfferClick}
          />
        )}
      </Card> */}
    </InternshipOffersProvider>
  );
};

export default InternshipOffers;
