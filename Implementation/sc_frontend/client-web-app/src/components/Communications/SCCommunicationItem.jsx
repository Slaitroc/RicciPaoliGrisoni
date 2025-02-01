import React from "react";
import { useNavigate } from "react-router-dom";
import * as logger from "../../logger/logger";
import { useCommunicationsContext } from "./CommunicationsContext";
import * as messages from "../../api-calls/api-wrappers/communication-wrapper/communication";
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
  ListItemButton,
} from "@mui/material";
import { SCLinkIcon } from "../Shared/SCIcons";

// SCMessageItem.jsx
export const SCCommunicationItem = ({ communication }) => {
  const { setMessagesData, setOpenAlert, setAlertMessage, setAlertSeverity } =
    useCommunicationsContext();

  const navigate = useNavigate();
  const clickOnInternship = (internshipOfferId) => {
    navigate(`/dashboard/internship-offer/details/${internshipOfferId}`);
  };
  const clickOnCommunication = async (communicationId) => {
    try {
      const response = await messages.getFormattedMessages(communicationId);
      if (!response.success) {
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      } else {
        logger.focus("Messages fetched successfully", response.data);
        setMessagesData(response.data);
      }
    } catch (error) {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("Error fetching communications");
      logger.focus("Error fetching communications", error);
    }
    navigate(`/dashboard/communications/details/${communicationId}`);
  };
  return (
    <>
      <ListItem
        secondaryAction={
          <Box display="flex" flexDirection="column" gap={1} paddingRight={4}>
            <Chip
              label={communication.type}
              variant="outlined"
              color={communication.type === "Complaint" ? "error" : ""}
            />
            <Chip
              label="View Internship"
              variant="outlined"
              icon={<SCLinkIcon />}
              onClick={(e) =>
                clickOnInternship(communication.internshipOfferID)
              }
            />
          </Box>
        }
      >
        <ListItemButton onClick={(e) => clickOnCommunication(communication.id)}>
          <Box display="flex" flexDirection="row" alignItems="center">
            <ListItemAvatar>
              <AvatarGroup max={3} spacing={20}>
                <Avatar alt="Company" src="/static/images/avatar/1.jpg" />
                <Avatar alt="Student" src="/static/images/avatar/2.jpg" />
                <Avatar alt="University" src="/static/images/avatar/3.jpg" />
              </AvatarGroup>
            </ListItemAvatar>
            <Box
              display="flex"
              flexDirection="column"
              paddingLeft={2}
              paddingRight={2}
              minWidth={240}
            >
              <Typography
                variant="h5"
                sx={{ fontWeight: "bold" }}
                maxWidth={240}
              >
                {communication.title}
              </Typography>
              <Typography
                variant="h8"
                sx={{ color: "text.secondary" }}
                maxWidth={240}
              >
                {communication.internshipOfferTitle}
              </Typography>
              <Typography variant="h10" sx={{ color: "text.secondary" }}>
                By:{" "}
                {communication.participantType === "student"
                  ? communication.studentName
                  : communication.companyName}
              </Typography>
            </Box>
            <Typography variant="h8" sx={{ color: "text.secondary" }}>
              {communication.content}
            </Typography>
          </Box>
        </ListItemButton>
      </ListItem>
      <Divider variant="inset" component="li" />
    </>
  );
};

// _____________________OLD CODE_____________________

// export const SCCommunicationItem = ({ message }) => {
//   return (
//     <>
//       <ListItem>
//         <Box display="flex" flexDirection="row">
//           <ListItemAvatar>
//             <AvatarGroup max={3} spacing={20}>
//               <Avatar alt="Company" src="/static/images/avatar/1.jpg" />
//               <Avatar alt="Student" src="/static/images/avatar/2.jpg" />
//               <Avatar alt="University" src="/static/images/avatar/3.jpg" />
//             </AvatarGroup>
//           </ListItemAvatar>
//           <ListItemText
//             sx={{ paddingLeft: 2 }}
//             primary={message.title}
//             secondary={
//               <>
//                 <Typography
//                   paddingRight={2}
//                   component="span"
//                   variant="body2"
//                   sx={{ color: "text.primary", display: "inline" }}
//                 >
//                   {message.timestamp}
//                 </Typography>
//                 <Typography
//                   paddingRight={1}
//                   component="span"
//                   variant="body"
//                   sx={{ color: "text.secondary", display: "inline" }}
//                 >
//                   {message.sender + ":"}
//                 </Typography>
//                 {message.body}
//               </>
//             }
//           />
//         </Box>
//         <Box
//           display="flex"
//           flexDirection="row"
//           alignItems="left"
//           paddingLeft={3}
//         >
//           <Chip
//             label={message.type}
//             color={message.type == "Complaint" ? "primary" : ""}
//           />
//         </Box>
//         <Box
//           display="flex"
//           flexDirection="row"
//           alignItems="left"
//           paddingLeft={3}
//         >
//           <Chip
//             label="See Internship"
//             variant="oulined"
//             icon={<SCLinkIcon />}
//           />
//         </Box>
//       </ListItem>
//       <Divider variant="inset" component="li" />
//     </>
//   );
// };
