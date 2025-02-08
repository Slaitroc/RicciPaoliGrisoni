import React from "react";
import { Paper, Typography, Box } from "@mui/material";

const formatUpdateTime = (updateTime) => {
  const messageDate = new Date(updateTime);
  const now = new Date();

  if (messageDate.toDateString() === now.toDateString()) {
    return messageDate.toLocaleTimeString([], {
      hour: "2-digit",
      minute: "2-digit",
    });
  } else {
    return messageDate.toLocaleString([], {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    });
  }
};

export const SCMessageItem = ({ message }) => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "flex-start",
        my: 1,
      }}
    >
      {/* Contenitore del messaggio */}
      <Paper
        elevation={2}
        sx={{
          borderRadius: 2,
          p: 1,
          paddingLeft: 2,
          maxWidth: 600,
          minWidth: 120,
        }}
      >
        {/* Nome del mittente */}
        <Typography variant="subtitle2" color="textSecondary">
          {message.senderName}
        </Typography>
        {/* Corpo del messaggio */}
        <Typography variant="body1">{message.body}</Typography>
      </Paper>

      {/* Timestamp posizionato al di fuori del rettangolo, in basso a destra */}
      <Box
        sx={{
          width: "100%",
          display: "flex",
          justifyContent: "flex-end",
          mt: 0.5,
        }}
      >
        <Typography variant="caption" color="textSecondary">
          {formatUpdateTime(message.updateTime)}
        </Typography>
      </Box>
    </Box>
  );
};

export default SCMessageItem;
