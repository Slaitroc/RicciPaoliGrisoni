import React, { useEffect } from "react";
import * as intTemplate from "../../api-calls/api-wrappers/Interview/interviewTemplate";
import * as logger from "../../logger/logger";
import * as apiCall from "../../api-calls/apiCalls";
import {
  Box,
  Button,
  Card,
  CardContent,
  Divider,
  Typography,
  List,
  ListItemButton,
  ListItemText,
  MenuItem,
  Menu,
} from "@mui/material";

const onAccept = (item, selectedStudent, onClose) => {
  try {
    if (!selectedStudent) {
      console.error("No student selected");
      return;
    }

    logger.debug("Selected Student Details:", {
      studentName: selectedStudent.studentName,
      internshipTitle: selectedStudent.internshipTitle,
      interviewID: selectedStudent.interviewID,
      itemID: item.id.value,
    });
    //should implement a error handling here
    apiCall.sendInterviewTemplate(item.id.value, selectedStudent.interviewID);
    onClose();
  } catch (error) {
    logger.error("Error in onAccept:", error);
  }
};

//main component
const InterviewTemplate = ({ item, onClose, onError }) => {
  const [matchNoInterview, setMatchNoInterview] = React.useState(null);

  useEffect(() => {
    intTemplate.getNoInterviewMatch().then((response) => {
      if (response.success === false) {
        onError(response);
        return;
      } else {
        logger.debug("response: ", response.data);
        const studentInterview = response.data.map((match) => {
          //logger.debug(match.studentName.value);
          //logger.debug(match.internshipTitle.value);
          return {
            studentName: match.studentName.value,
            internshipTitle: match.internshipTitle.value,
            interviewID: match.id.value,
          };
        });
        setMatchNoInterview(studentInterview);
      }
    });
  }, []);
  return (
    <>
      <Box
        sx={{
          position: "fixed",
          top: 0,
          left: 0,
          width: "100vw",
          height: "100vh",
          bgcolor: "rgba(0, 0, 0, 0.5)",
          backdropFilter: "blur(2px)",
          backgroundColor: "rgba(0, 0, 0, 0.5)",
          zIndex: 1000, //DO NOT PUT ON TOP OR THE MENU WILL NOT BE VISIBLE! (true story bro, i lost about 1 hour of my life redoing this shit)
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Box
          sx={{
            position: "relative",
            bgcolor: "background.paper",
            p: 6,
            borderRadius: 4,
            boxShadow: 24,
            textAlign: "center",
            maxWidth: 670,
            width: "90%",
          }}
        >
          <Card
            sx={{
              height: "auto",
              overflow: "auto",
              display: "flex",
              flexDirection: "column",
              marginBottom: 2,
            }}
          >
            <CardContent>
              <Typography
                variant="h5"
                gutterBottom
                color="text.primary"
                align="center"
              >
                {"Interview Template ID: " + item.id.value}
              </Typography>
              <Divider variant="middle" sx={{ my: 1 }} />
              {renderDetail("Question 1", item.question1.value)}
              {renderDetail("Question 2", item.question2.value)}
              {renderDetail("Question 3", item.question3.value)}
              {renderDetail("Question 4", item.question4.value)}
              {renderDetail("Question 5", item.question5.value)}
              {renderDetail("Question 6", item.question6.value)}
            </CardContent>
          </Card>
          <Box sx={{ display: "flex", justifyContent: "space-around", mt: 2 }}>
            <InteractiveBar
              students={matchNoInterview || []}
              onClose={onClose}
              item={item}
            />
          </Box>
        </Box>
      </Box>
    </>
  );
};

const InteractiveBar = ({ students, onClose, item }) => {
  const [selectedStudent, setSelectedStudent] = React.useState(null);

  const buttonStyles = {
    minWidth: "120px",
    padding: "8px 24px",
    transition: "all 0.2s ease-in-out",
  };

  return (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        width: "100%",
        gap: 2,
      }}
    >
      <Button
        variant="contained"
        onClick={() => onClose()}
        sx={{
          ...buttonStyles,
          backgroundColor: "#f44336",
          color: "white",
          "&:hover": {
            backgroundColor: "#d32f2f",
            transform: "scale(1.05)",
            boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
          },
        }}
      >
        Close
      </Button>
      <Box sx={{ flex: 1 }}>
        <StudentSelection
          students={students}
          onStudentSelect={(student) => setSelectedStudent(student)}
        />
      </Box>
      <Button
        variant="contained"
        onClick={() => onAccept(item, selectedStudent, onClose)}
        sx={{
          ...buttonStyles,
          backgroundColor: "#4caf50",
          color: "white",
          "&:hover": {
            backgroundColor: "#45a049",
            transform: "scale(1.05)",
            boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
          },
        }}
      >
        Assign
      </Button>
    </Box>
  );
};

//menu for selecting student
const StudentSelection = ({ students, onStudentSelect }) => {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [selectedIndex, setSelectedIndex] = React.useState(-1);
  const open = Boolean(anchorEl);
  const handleClickListItem = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleMenuItemClick = (event, index) => {
    setSelectedIndex(index);
    onStudentSelect(students[index]);
    setAnchorEl(null);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <>
      <List
        component="nav"
        aria-label="Student selection"
        sx={{
          bgcolor: "background.paper",
          width: "100%",
          "& .MuiListItemButton-root": {
            justifyContent: "center",
          },
        }}
      >
        <ListItemButton
          id="lock-button"
          aria-haspopup="listbox"
          aria-controls="lock-menu"
          aria-label="Select student"
          aria-expanded={open ? "true" : undefined}
          onClick={handleClickListItem}
        >
          <ListItemText
            primary="Select: Student - Internship"
            secondary={
              students[selectedIndex]?.studentName +
              " - " +
              students[selectedIndex]?.internshipTitle
            }
            sx={{
              textAlign: "center",
              "& .MuiTypography-root": {
                textAlign: "center",
              },
            }}
          />
        </ListItemButton>
      </List>
      <Menu
        id="lock-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          "aria-labelledby": "lock-button",
          role: "listbox",
        }}
      >
        {students.map((option, index) => (
          <MenuItem
            key={index}
            selected={index === selectedIndex}
            onClick={(event) => handleMenuItemClick(event, index)}
          >
            {`${option.studentName} - ${option.internshipTitle}`}
          </MenuItem>
        ))}
      </Menu>
    </>
  );
};

const renderDetail = (label, value) => {
  return (
    <Typography variant="body1" color="text.secondary">
      <Typography
        component="span"
        display="inline"
        variant="body2"
        sx={{ color: "text.primary" }}
      >
        {label}:
      </Typography>
      {" " + value}
    </Typography>
  );
};

export default InterviewTemplate;
