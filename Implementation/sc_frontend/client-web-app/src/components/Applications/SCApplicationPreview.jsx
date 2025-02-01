import React from "react";
import { Grid2, Card, CardContent, Typography } from "@mui/material";
import { useApplicationContext } from "./ApplicationsContext";
import * as logger from "../../logger/logger";
import SCApplication from "./SCApplication";
import { useGlobalContext } from "../../global/GlobalContext";

// Main component
export const SCApplicationPreview = () => {
  const { applicationData } = useApplicationContext();
  const [selectedItem, setSelectedItem] = React.useState(null);
  const { profile } = useGlobalContext();

  const handleClick = (item) => {
    setSelectedItem(item);
  };

  const handleClose = () => {
    setSelectedItem(null);
  };

  //Utils to render details
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
      {selectedItem && (
        <SCApplication
          item={selectedItem}
          onClose={handleClose}
          profile={profile}
        />
      )}
      {applicationData && (
        <Grid2 container spacing={3} padding={5}>
          {applicationData.map((item) => (
            <PreviewCard
              key={item.id}
              item={item}
              renderDetail={renderDetail}
              onClick={() => handleClick(item)}
            />
          ))}
        </Grid2>
      )}
    </>
  );
};

// Private component - only used within SCApplicationPreview
const PreviewCard = ({ item, onClick, renderDetail }) => {
  return (
    <Card
      onClick={onClick}
      sx={{
        height: "auto",
        width: 500,
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
          {item.internshipOfferTitle}
        </Typography>
        {renderDetail("Student Name", item.studentName)}
        {renderDetail("Company Name", item.internshipOfferCompanyName)}
        {renderDetail("Status", item.status)}
      </CardContent>
    </Card>
  );
};

export default SCApplicationPreview;
