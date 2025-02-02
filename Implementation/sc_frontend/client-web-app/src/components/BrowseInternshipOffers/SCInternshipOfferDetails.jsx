import React from "react";
import { useBrowseInternshipContext } from "./BrowseInternshipContext";
import * as logger from "../../logger/logger";
import {
  Box,
  Typography,
  Button,
  Card,
  CardContent,
  Divider,
} from "@mui/material";
import * as application from "../../api-calls/api-wrappers/submission-wrapper/spontaneousApplication";

const SCInternshipOfferDetails = ({ offer, onClose, profile }) => {
  const { setOpenAlert, setAlertMessage, setAlertSeverity } =
    useBrowseInternshipContext();

  const renderDetail = (label, value) => (
    <Typography variant="body1" color="text.secondary" sx={{ my: 1 }}>
      <Typography
        component="span"
        display="inline"
        variant="body2"
        sx={{ color: "text.primary", fontWeight: "bold" }}
      >
        {label}:
      </Typography>
      {" " + value}
    </Typography>
  );

  const Buttons = ({ onClose, profile, offer }) => {
    const buttonStyles = {
      minWidth: "120px",
      padding: "8px 24px",
      transition: "all 0.2s ease-in-out",
    };

    // Se l'utente è uno STUDENT mostra il pulsante per candidarsi e il pulsante Close
    if (profile.userType === "STUDENT") {
      return (
        <>
          <Button
            variant="contained"
            onClick={() => handleApply(offer, onClose)}
            sx={{
              ...buttonStyles,
              backgroundColor: "#4caf50",
              color: "white",
              "&:hover": {
                backgroundColor: "#45a049",
                transform: "scale(1.05)",
                boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
              },
            }}
          >
            Apply
          </Button>
          <Button
            variant="contained"
            onClick={onClose}
            sx={{
              ...buttonStyles,
              backgroundColor: "#ffffff",
              color: "#000000",
              "&:hover": {
                backgroundColor: "#f5f5f5",
                transform: "scale(1.05)",
                boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
              },
            }}
          >
            Close
          </Button>
        </>
      );
    } else {
      // Se l'utente è una COMPANY mostra solo il pulsante Close
      return (
        <Button
          variant="contained"
          onClick={onClose}
          sx={{
            ...buttonStyles,
            backgroundColor: "#ffffff",
            color: "#000000",
            "&:hover": {
              backgroundColor: "#f5f5f5",
              transform: "scale(1.05)",
              boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
            },
          }}
        >
          Close
        </Button>
      );
    }
  };

  const handleApply = (offer, onClose) => {
    //console.log("Applying to offer:", offer);
    try {
      application.submitSpontaneousApplication(offer.id).then((response) => {
        logger.debug(response);
        if (response.success === false) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        }
        onClose();
      });
    } catch (error) {
      throw error;
    }
  };

  return (
    <Box
      sx={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100vw",
        height: "100vh",
        bgcolor: "rgba(0, 0, 0, 0.5)",
        backdropFilter: "blur(2px)",
        zIndex: 9999,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <Box
        sx={{
          position: "relative",
          bgcolor: "background.paper",
          p: 6,
          borderRadius: 4,
          boxShadow: 24,
          textAlign: "center",
          maxWidth: 670,
          width: "90%",
        }}
      >
        <Card
          sx={{
            height: "auto",
            display: "flex",
            flexDirection: "column",
            marginBottom: 2,
          }}
        >
          <CardContent>
            <Typography variant="h4" gutterBottom color="text.primary">
              {offer.title}
            </Typography>
            <Divider variant="middle" sx={{ my: 1 }} />
            {renderDetail("Company", offer.companyName)}
            {renderDetail("Title", offer.title)}
            {renderDetail("Description", offer.description)}
            {renderDetail("Skill Required", offer.requiredSkills)}
            {renderDetail("Compensation", offer.compensation)}
            {renderDetail("Location", offer.location)}
            {renderDetail("Start Date", offer.startDate)}
            {renderDetail("End Date", offer.endDate)}
            {renderDetail("Duration", offer.duration)}
            {renderDetail("Positions", offer.numberPositions)}
          </CardContent>
        </Card>
        <Box sx={{ display: "flex", justifyContent: "space-around", mt: 2 }}>
          <Buttons onClose={onClose} profile={profile} offer={offer} />
        </Box>
      </Box>
    </Box>
  );
};

export default SCInternshipOfferDetails;
