import React, { useState } from "react";
import NotificationsRoundedIcon from "@mui/icons-material/NotificationsRounded";
import SCMenuButton from "../Dashboard/SCMenuButton";
import Menu from "@mui/material/Menu";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import * as apiCalls from "../../api-calls/apiCalls";
import * as logger from "../../logger/logger";
import { useGlobalContext } from "../../global/GlobalContext";

export const NotificationContainer = () => {
  const { setShowNotificationAlert, showNotificationAlert } =
    useGlobalContext();
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const [notifications, setNotifications] = useState([]);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
    apiCalls.getNotifications().then((response) => {
      response.json().then((data) => {
        // logger.debug("incoming data", data);
        const sortedData = data.sort((a, b) => b.id - a.id);
        setNotifications(sortedData);
        setShowNotificationAlert(false);
      });
    });
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <>
      <SCMenuButton
        showBadge={showNotificationAlert}
        aria-label="Open notifications"
        onClick={handleClick}
      >
        <NotificationsRoundedIcon />
      </SCMenuButton>

      <Menu
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        PaperProps={{
          sx: {
            width: 500,
            overflowY: "auto",
          },
        }}
      >
        <Box p={2}>
          {/*remidner the && notification allow to wait for the notification to actually be set by the fetch before checking the length*/}
          {open && notifications && notifications.length !== 0
            ? notifications.map((notification) => (
                <Box
                  key={notification.id}
                  sx={{
                    p: 2,
                    borderBottom: "1px solid",
                    borderColor: "divider",
                    "&:last-child": { borderBottom: "none" },
                    "&:hover": { backgroundColor: "action.hover" },
                  }}
                >
                  <div
                    style={{
                      fontWeight: "bold",
                      fontSize: "0.875rem",
                      mb: 0.5,
                    }}
                  >
                    {notification.title}
                  </div>
                  <div
                    style={{
                      color: "text.secondary",
                      fontSize: "0.875rem",
                      lineHeight: 1.4,
                    }}
                  >
                    {notification.body}
                  </div>
                </Box>
              ))
            : (null, (<div>No notification received yet</div>))}
        </Box>
      </Menu>
    </>
  );
};

export default NotificationContainer;
