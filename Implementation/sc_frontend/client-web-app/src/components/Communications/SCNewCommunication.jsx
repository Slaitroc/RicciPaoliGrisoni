import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as communication from "../../api-calls/api-wrappers/communication-wrapper/communication";
import { useGlobalContext } from "../../global/GlobalContext";
import {
  Box,
  Typography,
  Button,
  TextField,
  MenuItem,
  FormControl,
  FormLabel,
  Alert,
  IconButton,
  Badge,
  badgeClasses,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { SCSendIcon } from "../Shared/SCIcons";
import { useCommunicationsContext } from "./CommunicationsContext";
import { log, focus, debug } from "../../logger/logger";

export default function SCNewCommunication() {
  const navigate = useNavigate();
  const { internshipPositionOffers } = useCommunicationsContext();
  const { profile } = useGlobalContext();
  const { type } = useParams();
  const communicationType =
    type === "complaint" ? "Complaint" : "Communication";

  const [formData, setFormData] = useState({
    title: "",
    content: "",
    selectedOffer: "",
  });

  const [errors, setErrors] = useState({
    title: false,
    content: false,
    selectedOffer: false,
  });

  // const [openTipAlert, setOpenTipAlert] = useState(true);

  const handleChange = (field, value) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
    setErrors((prev) => ({ ...prev, [field]: value === "" }));
  };

  const handleSubmit = () => {
    const newErrors = {
      title: formData.title === "",
      content: formData.content === "",
      selectedOffer: formData.selectedOffer === "",
    };

    setErrors(newErrors);

    const toBeSentData = {
      title: formData.title,
      content: formData.content,
      internshipPosOfferID: formData.selectedOffer.id,
      communicationType: type,
    };

    if (!Object.values(newErrors).includes(true)) {
      focus("To be send data:", toBeSentData);
      // navigate("/dashboard/communications");
    }
    communication.createCommunication(toBeSentData);
  };

  return (
    <Box display="flex" flexDirection="column" gap={2} sx={{ p: 3 }}>
      {/* Barra superiore con back button */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Badge
          color="error"
          variant="dot"
          invisible={true}
          sx={{ [`& .${badgeClasses.badge}`]: { right: 2, top: 2 } }}
        >
          <IconButton
            size="small"
            onClick={() => navigate("/dashboard/communications")}
          >
            <ArrowBackIcon />
          </IconButton>
        </Badge>
        <Typography variant="h5">New {communicationType}</Typography>
      </Box>

      {/* Alert con suggerimenti */}
      {/* {openTipAlert && (
        <Alert severity="info" onClose={() => setOpenTipAlert(false)}>
          <Typography variant="body1">
            Provide a clear and concise title. Use keywords to enhance
            communication effectiveness.
          </Typography>
        </Alert>
      )} */}

      {/* Form di input */}
      <Box sx={{ p: 2, border: "1px solid gray", borderRadius: 2 }}>
        <Box display="flex" flexDirection="row" gap={2}>
          {/* Titolo */}
          <FormControl fullWidth>
            <FormLabel>Title</FormLabel>
            <TextField
              variant="outlined"
              value={formData.title}
              error={errors.title}
              helperText={errors.title ? "Title is required" : ""}
              onChange={(e) => handleChange("title", e.target.value)}
            />
          </FormControl>

          {/* Internship Offer Selection */}
          <FormControl fullWidth>
            <FormLabel>Internship Offer</FormLabel>
            <TextField
              select
              variant="outlined"
              value={formData.selectedOffer}
              error={errors.selectedOffer}
              helperText={
                errors.selectedOffer ? "Please select an internship offer" : ""
              }
              onChange={(e) => {
                handleChange("selectedOffer", e.target.value);
              }}
            >
              {internshipPositionOffers?.length > 0 ? (
                internshipPositionOffers.map((offer) => {
                  const otherUserName =
                    offer.studentName !== profile.name
                      ? offer.studentName
                      : offer.companyName;
                  return (
                    <MenuItem key={offer.id} value={offer}>
                      {offer.internshipTitle} - {otherUserName}
                    </MenuItem>
                  );
                })
              ) : (
                <MenuItem disabled>No internship offers available</MenuItem>
              )}
            </TextField>
          </FormControl>
        </Box>

        {/* Descrizione */}
        <FormControl fullWidth>
          <FormLabel>Description</FormLabel>
          <TextField
            variant="outlined"
            multiline
            value={formData.content}
            error={errors.content}
            helperText={errors.content ? "Description is required" : ""}
            onChange={(e) => handleChange("content", e.target.value)}
          />
        </FormControl>

        {/* Pulsante di invio */}
        <Box mt={3} display="flex" justifyContent="center">
          <Button
            variant="contained"
            color="primary"
            startIcon={<SCSendIcon />}
            onClick={handleSubmit}
          >
            Submit
          </Button>
        </Box>
      </Box>
    </Box>
  );
}
