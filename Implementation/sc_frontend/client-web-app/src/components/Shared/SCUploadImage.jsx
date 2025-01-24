import React, { useState } from "react";
import { Box, Button, Card, Typography, Avatar } from "@mui/material";
import { useGlobalContext } from "../../global/GlobalContext";

export default function SCUploadImage() {
  const { removePhoto, handleFileChange, previewUrl, selectedFile } =
    useGlobalContext();

  return (
    <>
      <Typography variant="h5" gutterBottom>
        Carica una Foto
      </Typography>
      <Box display="flex" flexDirection="row" gap={5}>
        {!previewUrl && (
          <Button variant="contained" component="label" sx={{ mb: 2 }}>
            Carica Foto
            <input
              type="file"
              accept="image/*"
              hidden
              onChange={handleFileChange}
            />
          </Button>
        )}

        {/* Mostra l'anteprima dell'immagine caricata */}
        {previewUrl && (
          <Card
            sx={{
              p: 2,
              mb: 2,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Avatar
              src={previewUrl}
              alt="Preview"
              sx={{ width: 120, height: 120, mb: 2 }}
            />
            <Typography variant="body1">
              Nome del file: {selectedFile?.name || "N/A"}
            </Typography>
            <Button
              variant="outlined"
              color="error"
              onClick={removePhoto}
              sx={{ mt: 2 }}
            >
              Rimuovi Foto
            </Button>
          </Card>
        )}
      </Box>
    </>
  );
}
