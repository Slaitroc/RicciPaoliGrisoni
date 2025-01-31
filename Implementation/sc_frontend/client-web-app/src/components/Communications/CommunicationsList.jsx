import React from "react";
import { List, Typography, Box, Button } from "@mui/material";
import * as logger from "../../logger/logger";
import { useCommunicationsContext } from "./CommunicationsContext";
import { SCCommunicationItem } from "./SCCommunicationItem";
import SCCommunications from "./SCCommunications";

export const CommunicationsList = () => {
  const { communicationsData } = useCommunicationsContext();

  return (
    <List
      sx={{
        width: "100%",
        bgcolor: "background.paper",
        position: "relative",
        overflow: "auto",
        maxHeight: 550,
      }}
    >
      {communicationsData?.map((data) => (
        <SCCommunicationItem key={data.id} communication={data} />
      ))}
    </List>
  );
};

export default CommunicationsList;
