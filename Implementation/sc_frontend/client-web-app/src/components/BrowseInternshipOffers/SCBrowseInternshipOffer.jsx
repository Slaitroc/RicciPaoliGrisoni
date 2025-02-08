import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Box, Avatar, Typography, Button, Alert } from "@mui/material";
import { v4 as uuidv4 } from "uuid";
import { useBrowseInternshipContext } from "./BrowseInternshipContext";
import { useGlobalContext } from "../../global/GlobalContext";
import * as internshipOffer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";

const SCBrowseInternshipOffer = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [offer, setOffer] = useState({});
  const { setOpenAlert, setAlertMessage, setAlertSeverity } =
    useBrowseInternshipContext();
  const { previewUrl, profile } = useGlobalContext();

  useEffect(() => {
    const fetchOffer = async () => {
      try {
        const response = await internshipOffer.getSpecificOffer(id);
        if (!response.success) {
          console.error(response.message);
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        } else {
          setOffer(response.data);
        }
      } catch (error) {
        console.error("Error fetching offer:", error);
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage(error.message);
      }
    };
    fetchOffer();
  }, [id, setOpenAlert, setAlertMessage, setAlertSeverity]);

  return (
    <Box display="flex" flexDirection="column" height="100%" gap={2} p={2}>
      <Box sx={{ mt: 2, p: 2, border: "1px solid gray", borderRadius: 2 }}>
        <Box
          display="flex"
          flexDirection="row"
          justifyContent="space-between"
          alignItems="center"
        >
          <Box
            display="flex"
            flexDirection="row"
            gap={1}
            alignItems="center"
            paddingBottom={5}
          >
            <Avatar src={previewUrl} alt="Company Logo" />
            <Typography variant="h5" gutterBottom>
              Internship Offer Details
            </Typography>
          </Box>
          <Box display="flex" flexDirection="column" alignItems="flex-end">
            {offer.updateTime && offer.updateTime.value && (
              <Typography variant="h6" gutterBottom>
                Last Update:{" "}
                {new Date(offer.updateTime.value).toLocaleString("it-IT", {
                  year: "numeric",
                  month: "2-digit",
                  day: "2-digit",
                  hour: "2-digit",
                  minute: "2-digit",
                })}
              </Typography>
            )}
            {/* Se volessi aggiungere un pulsante Edit, puoi decommentare il codice */}
            {/* <Button
              variant="outlined"
              sx={{
                whiteSpace: "nowrap",
                width: "50%",
                paddingX: 6,
              }}
              onClick={() => navigate(`/dashboard/internship-offers/edit/${id}`)}
            >
              Edit Offer
            </Button> */}
          </Box>
        </Box>

        {/* Sezione con il nome dell'azienda */}
        <Box sx={{ mb: 2 }}>
          <Box display="flex" flexDirection="column" gap={1}>
            <Typography variant="h6">Company Name:</Typography>
            <Typography
              variant="body1"
              whiteSpace="pre-line"
              color="text.secondary"
            >
              {offer.companyName
                ? offer.companyName.value
                : "No content provided."}
            </Typography>
          </Box>
        </Box>

        {/* Itera su tutti i campi dell'offerta, escludendo quelli che non vuoi mostrare */}
        {Object.entries(offer).map(([key, field]) => {
          if (
            key === "companyID" ||
            key === "id" ||
            key === "updateTime" ||
            key === "companyName"
          )
            return null;

          return (
            <Box key={uuidv4()} sx={{ mb: 2 }}>
              <Box display="flex" flexDirection="column" gap={1}>
                <Typography variant="h6">
                  {field.label ? field.label : key}:
                </Typography>
                <Typography
                  variant="body1"
                  whiteSpace="pre-line"
                  color="text.secondary"
                >
                  {field.value && field.value !== 0
                    ? field.value.toString()
                    : "No content provided."}
                </Typography>
              </Box>
            </Box>
          );
        })}
      </Box>
    </Box>
  );
};

export default SCBrowseInternshipOffer;
