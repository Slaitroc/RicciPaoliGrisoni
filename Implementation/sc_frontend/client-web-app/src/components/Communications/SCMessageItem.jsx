import React from "react";
import {
  ListItem,
  ListItemAvatar,
  AvatarGroup,
  Avatar,
  ListItemText,
  Typography,
  Divider,
  Box,
  Chip,
} from "@mui/material";
import { SCLinkIcon } from "../Shared/SCIcons";

export const SCCommunicationItem = ({ message }) => {
  return (
    <>
      <ListItem>
        <Box display="flex" flexDirection="row">
          <ListItemAvatar>
            <AvatarGroup max={3} spacing={20}>
              <Avatar alt="Company" src="/static/images/avatar/1.jpg" />
              <Avatar alt="Student" src="/static/images/avatar/2.jpg" />
              <Avatar alt="University" src="/static/images/avatar/3.jpg" />
            </AvatarGroup>
          </ListItemAvatar>
          <ListItemText
            sx={{ paddingLeft: 2 }}
            primary={message.title}
            secondary={
              <>
                <Typography
                  paddingRight={2}
                  component="span"
                  variant="body2"
                  sx={{ color: "text.primary", display: "inline" }}
                >
                  {message.timestamp}
                </Typography>
                <Typography
                  paddingRight={1}
                  component="span"
                  variant="body"
                  sx={{ color: "text.secondary", display: "inline" }}
                >
                  {message.sender + ":"}
                </Typography>
                {message.body}
              </>
            }
          />
        </Box>
        <Box
          display="flex"
          flexDirection="row"
          alignItems="left"
          paddingLeft={3}
        >
          <Chip
            label={message.type}
            color={message.type == "Complaint" ? "primary" : ""}
          />
        </Box>
        <Box
          display="flex"
          flexDirection="row"
          alignItems="left"
          paddingLeft={3}
        >
          <Chip
            label="See Internship"
            variant="oulined"
            icon={<SCLinkIcon />}
          />
        </Box>
      </ListItem>
      <Divider variant="inset" component="li" />
    </>
  );
};
