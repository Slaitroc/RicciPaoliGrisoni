import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  FormControl,
  FormLabel,
  TextField,
  Button,
  Card,
  Box,
  Badge,
  IconButton,
  badgeClasses,
  Typography,
  Alert,
} from "@mui/material";
import * as logger from "../../logger/logger";
import Grid from "@mui/material/Grid2";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import * as cv from "../../api-calls/api-wrappers/submission-wrapper/cv";
import { useInternshipOffersContext } from "./InternshipOffersContext";

export const SCIntOfferEdit = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const {
    offerDataSnapshot,
    setOpenAlert,
    setAlertMessage,
    setAlertSeverity,
    updateInternshipOffer,
  } = useInternshipOffersContext();
  const newOfferDataRef = React.useRef(offerDataSnapshot);
  const [openTipAlert, setOpenTipAlert] = React.useState(true);

  const handleFieldChange = (field, value) => {
    newOfferDataRef.current[field].value = value;
  };

  const clickBack = () => {
    //NAV to CV
    navigate(`/dashboard/internship-offers/details/${id}`);
  };

  const clickUpdateCvData = () => {
    logger.focus(newOfferDataRef.current);
    const updatedData = { ...newOfferDataRef.current };

    setCvData(updatedData);
    logger.log("updateCvData", updatedData);
    cv.updateMyCV(updatedData)
      .then((response) => {
        if (response.success) {
          setOpenAlert(true);
          setAlertMessage(response.message);
          setAlertSeverity(response.severity);
          setTimeout(() => {
            setOpenAlert(false);
          }, 5000);
        } else {
          setOpenAlert(true);
          setAlertMessage(response.message);
          setAlertSeverity(response.severity);
        }
      })
      .catch((error) => {
        console.error("Error during updateMyCV:", error);
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("Error while updating CV data...");
      });
  };

  return (
    <Box display="flex" flexDirection="column" height="100%" gap={2}>
      <Box sx={{ mt: 2, p: 2, border: "1px solid gray", borderRadius: 2 }}>
        <Box
          display="flex"
          paddingBottom={3}
          flexDirection="row"
          justifyContent="space-between"
          alignItems="center"
        >
          <Badge
            color="error"
            variant="dot"
            invisible={true}
            sx={{ [`& .${badgeClasses.badge}`]: { right: 2, top: 2 } }}
          >
            <IconButton size="small" onClick={clickBack}>
              <ArrowBackIcon />
            </IconButton>
          </Badge>
          <Typography variant="h6 " gutterBottom>
            Last Update:{" "}
            {new Date().toLocaleString("it-IT", {
              year: "numeric",
              month: "2-digit",
              day: "2-digit",
              hour: "2-digit",
              minute: "2-digit",
            })}
          </Typography>
        </Box>
        {openTipAlert && (
          <Alert
            severity="info"
            sx={{ mb: 2 }}
            onClose={() => setOpenTipAlert(false)}
          >
            <Typography variant="body1">
              Edit your data and click Save Changes to update your Internship
              Offer.
            </Typography>
            <Typography variant="h6" inline="true">
              Remember:
              <Typography inline="true" variant="body1">
                Using keywords instead of long phrases increases the chances of
                getting a match in the recommendation process.
              </Typography>
            </Typography>
          </Alert>
        )}
        <Grid
          container
          spacing={3}
          display={{ xs: "block", sm: "flex" }}
          columns={12}
          alignItems="stretch"
        >
          {offerDataSnapshot &&
            Object.entries(offerDataSnapshot).map(([key, value]) => {
              if (
                key !== "id" &&
                key !== "companyID" &&
                key !== "companyName" &&
                key !== "updateTime"
              ) {
                return (
                  <Grid
                    item="true"
                    size={{ xs: 12, sm: 12, md: 12, lg: 6 }}
                    key={key}
                  >
                    <Box display="flex">
                      <FormControl sx={{ flexGrow: 1 }}>
                        <FormLabel>{value.label}</FormLabel>
                        <TextField
                          multiline
                          variant="outlined"
                          defaultValue={value.value}
                          name={key}
                          onBlur={(e) => handleFieldChange(key, e.target.value)}
                          sx={{
                            flexGrow: 1,
                            "& .MuiOutlinedInput-root": {
                              minHeight: "auto",
                              height: "auto",
                            },
                          }}
                        />
                      </FormControl>
                    </Box>
                  </Grid>
                );
              } else return null;
            })}
        </Grid>

        {/* Pulsante per inviare tutti i dati */}
        <Box mt={3} display="flex" justifyContent="center">
          <Button
            variant="contained"
            color="primary"
            onClick={() => updateInternshipOffer(id, newOfferDataRef.current)}
          >
            Update Offer
          </Button>
        </Box>
      </Box>
    </Box>
  );
};
