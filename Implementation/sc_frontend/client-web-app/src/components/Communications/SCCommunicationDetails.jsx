import React, { useState, useRef, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Box, Grid2, TextField, IconButton } from "@mui/material";
import { SCSendIcon, SCCachedIcon } from "../Shared/SCIcons";
import { useGlobalContext } from "../../global/GlobalContext";
import { useCommunicationsContext } from "./CommunicationsContext";
import SCMessageItem from "./SCMessageItem";
import { focus } from "../../logger/logger";
import * as message from "../../api-calls/api-wrappers/communication-wrapper/communication";

const SCCommunicationDetails = () => {
  const { id } = useParams();
  const {
    messagesData,
    setOpenAlert,
    setAlertMessage,
    setAlertSeverity,
    setMessagesData,
  } = useCommunicationsContext();
  const { profile } = useGlobalContext();
  const [messageText, setMessageText] = useState("");

  const containerRef = useRef(null);

  useEffect(() => {
    if (containerRef.current) {
      containerRef.current.scrollTo({
        top: containerRef.current.scrollHeight,
        behavior: "smooth",
      });
    }
  }, [messagesData]);

  const reloadMessages = async () => {
    try {
      const response = await message.getFormattedMessages(id);
      if (!response.success) {
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      } else {
        setOpenAlert(false);
        focus("Messages fetched successfully", response.data);
        setMessagesData(response.data);
      }
    } catch (error) {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("Error fetching communications");
      focus("Error fetching communications", error);
    }
  };

  const sendMessageTrigger = async () => {
    if (messageText !== "") {
      const toBeSentData = { body: messageText };
      if (messageText.length > 6000) {
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("Message too long");
        return;
      }
      focus("Sending message", toBeSentData);
      try {
        const response = await message.sendMessage(id, toBeSentData);
        if (!response.success) {
          setOpenAlert(true);
          setAlertSeverity(response.severity);
          setAlertMessage(response.message);
        }
      } catch (error) {
        setOpenAlert(true);
        setAlertSeverity("error");
        setAlertMessage("Error sending message");
      }
      setMessageText("");
      reloadMessages();
    }
  };

  return (
    // Il container principale occupa l'85% dell'altezza della viewport
    <Box sx={{ display: "flex", flexDirection: "column", height: "85vh" }}>
      {/* Container scrollabile dei messaggi */}
      <Box
        ref={containerRef}
        sx={{
          flexGrow: 1,
          overflowY: "auto",
          display: "flex",
          flexDirection: "column",
          justifyContent: "flex-end",
          p: 2,
          bgcolor: "background.paper",
        }}
      >
        <Grid2 sx={{ width: "100%", minHeight: 0 }}>
          {messagesData?.map((data) => (
            <Box
              key={data.id}
              sx={{
                display: "flex",
                justifyContent:
                  data.senderName !== profile.name ? "flex-start" : "flex-end",
                mb: 1,
              }}
            >
              <SCMessageItem message={data} />
            </Box>
          ))}
        </Grid2>
      </Box>

      {/* Barra di input */}
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          p: 1,
          borderTop: "1px solid #ccc",
        }}
      >
        <TextField
          fullWidth
          variant="outlined"
          placeholder="Write a message..."
          value={messageText}
          onChange={(e) => setMessageText(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              e.preventDefault();
              sendMessageTrigger();
            }
          }}
        />
        <IconButton sx={{ ml: 1 }} onClick={sendMessageTrigger}>
          <SCSendIcon />
        </IconButton>
        <IconButton sx={{ ml: 1 }} onClick={reloadMessages}>
          <SCCachedIcon />
        </IconButton>
      </Box>
    </Box>
  );
};

export default SCCommunicationDetails;
