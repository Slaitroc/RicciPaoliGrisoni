import React from "react";
import { Grid2, Card, CardContent, Typography } from "@mui/material";
import * as logger from "../../logger/logger";
import { useIntPosOfferContext } from "./IntPosOfferContext";
import { useGlobalContext } from "../../global/GlobalContext";
import IntPosOffer from "./IntPosOffer";

// Main component
export const IntPosOfferPreview = () => {
  const { intPosOfferData, setOpenAlert, openAlert } = useIntPosOfferContext();
  const [selectedItem, setSelectedItem] = React.useState(null);
  const { profile } = useGlobalContext();

  const handleClick = (item) => {
    setOpenAlert(false);
    setSelectedItem(item);
  };

  const handleClose = () => {
    setSelectedItem(null);
  };

  return (
    <>
      {selectedItem && (
        <IntPosOffer
          item={selectedItem}
          onClose={handleClose}
          profile={profile}
        />
      )}
      {intPosOfferData && (
        <Grid2 padding={5} container spacing={3}>
          {intPosOfferData.map((item) => {
            //logger.debug("item: ", item);
            return (
              <IntPosOfferCard
                key={item.id}
                item={item}
                onClick={() => handleClick(item)}
              ></IntPosOfferCard>
            );
          })}
        </Grid2>
      )}
    </>
  );
};

const IntPosOfferCard = ({ item, onClick }) => {
  return (
    <Card
      onClick={() => onClick(item)}
      sx={{
        height: "auto",
        width: 500,
        display: "flex",
        flexDirection: "column",
        "&:hover, &:focus-visible": {
          backgroundColor: "rgba(255, 255, 255, 0.8)",
          cursor: "pointer",
          ".MuiTypography-body1": {
            color: "gray",
          },
          ".MuiTypography-body2, .MuiTypography-h5": {
            color: "Black",
          },
          // Target status text specifically on hover
          ".status-text .MuiTypography-body1": {
            color: (theme) =>
              item.status === "accepted"
                ? theme.palette.success.main
                : item.status === "rejected"
                ? theme.palette.error.main
                : theme.palette.text.secondary,
            fontWeight: "bold",
          },
          outline: "3px solid",
          outlineColor:
            item.status === "accepted"
              ? "green"
              : item.status === "rejected"
              ? "red"
              : "hsla(210, 98%, 48%, 0.5)",
          outlineOffset: "2px",
        },
      }}
    >
      <CardContent>
        <Typography variant="h5" gutterBottom color="text.primary">
          {item.internshipTitle}
        </Typography>
        {renderDetail("Company Name", item.companyName)}
        {/* Wrapped status text for specific styling */}
        <div className="status-text">{renderDetail("Status", item.status)}</div>
        {renderDetail("Interview ID", item.interviewID)}
      </CardContent>
    </Card>
  );
};

const renderDetail = (label, value) => {
  return (
    <Typography variant="body1" color="text.secondary">
      <Typography
        component="span"
        display="inline"
        variant="body2"
        sx={{ color: "text.primary" }}
      >
        {label}:
      </Typography>
      {" " + value}
    </Typography>
  );
};

export default IntPosOfferPreview;
