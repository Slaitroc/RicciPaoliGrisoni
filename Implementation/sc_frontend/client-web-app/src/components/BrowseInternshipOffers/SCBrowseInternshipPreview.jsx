import React from "react";
import { Grid2, Card, CardContent, Typography } from "@mui/material";
import { useBrowseInternshipContext } from "./BrowseInternshipContext";
import { useGlobalContext } from "../../global/GlobalContext";
import SCInternshipOfferDetails from "./SCInternshipOfferDetails";
import { log, focus } from "../../logger/logger";

export const SCInternshipOfferPreview = () => {
  const { offerData } = useBrowseInternshipContext();
  const [selectedOffer, setSelectedOffer] = React.useState(null);
  const { profile } = useGlobalContext();

  const handleClick = (offer) => {
    setSelectedOffer(offer);
  };

  const handleClose = () => {
    setSelectedOffer(null);
  };

  // Funzione di utilità per mostrare i dettagli nella preview
  const renderDetail = (label, value) => (
    <Typography variant="body1" color="text.secondary">
      <Typography
        component="span"
        display="inline"
        variant="body2"
        sx={{ color: "text.primary" }}
      >
        {label}:
      </Typography>
      {` ${value}`}
    </Typography>
  );

  return (
    <>
      {selectedOffer && (
        <SCInternshipOfferDetails
          offer={selectedOffer}
          onClose={handleClose}
          profile={profile}
        />
      )}
      {offerData && (
        <Grid2 container spacing={3} padding={5}>
          {offerData.map((offer) => (
            <PreviewCard
              key={offer.id}
              offer={offer}
              onClick={() => handleClick(offer)}
              renderDetail={renderDetail}
            />
          ))}
        </Grid2>
      )}
    </>
  );
};

const PreviewCard = ({ offer, onClick, renderDetail }) => {
  return (
    <Card
      onClick={onClick}
      sx={{
        height: "auto",
        width: "20vw",
        display: "flex",
        flexDirection: "column",
        marginBottom: 2,
        "&:hover, &:focus-visible": {
          backgroundColor: "rgba(255, 255, 255, 0.8)",
          cursor: "pointer",
          ".MuiTypography-body1": { color: "gray" },
          ".MuiTypography-body2, .MuiTypography-h5": { color: "Black" },
          outline: "3px solid",
          outlineColor: "hsla(210, 98%, 48%, 0.5)",
          outlineOffset: "2px",
        },
      }}
    >
      <CardContent>
        <Typography variant="h5" gutterBottom color="text.primary">
          {offer.title}
        </Typography>
        {renderDetail("Company", offer.companyName)}
        {renderDetail("Title", offer.title)}
        {renderDetail("Description", offer.description)}
        {renderDetail("Compensation", offer.compensation)}
        {renderDetail("Duration", offer.duration)}
      </CardContent>
    </Card>
  );
};

export default SCInternshipOfferPreview;
