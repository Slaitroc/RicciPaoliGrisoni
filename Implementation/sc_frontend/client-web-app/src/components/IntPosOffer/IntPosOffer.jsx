import React, { useEffect } from "react";
import * as apiCall from "../../api-calls/apiCalls";
import { useIntPosOfferContext } from "./IntPosOfferContext";
import * as cv from "../../api-calls/api-wrappers/submission-wrapper/cv";
import * as offer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";
import * as logger from "../../logger/logger";
import {
  Box,
  Typography,
  Button,
  Card,
  CardContent,
  Divider,
} from "@mui/material";
//todo show alert can accept more than one

const onAccept = (item, onClose, intPosOfferData, setIntPosOfferData) => {
  logger.debug("Accepted positionOffer", item);
  apiCall.acceptInternshipPositionOffer(item.id);

  const updatedIntPosOffer = intPosOfferData.map((intPosOff) => {
    if (intPosOff.id === item.id) {
      return { ...intPosOff, status: "accepted" };
    }
    return application;
  });
  console.log(updatedIntPosOffer);
  setIntPosOfferData(updatedIntPosOffer);
  onClose();
};

const onReject = (item, onClose, intPosOfferData, setIntPosOfferData) => {
  logger.debug("Rejected positionOffer", item);
  apiCall.rejectInternshipPositionOffer(item.id);
  const updatedIntPosOffer = intPosOfferData.map((intPosOff) => {
    if (intPosOff.id === item.id) {
      return { ...intPosOff, status: "rejected" };
    }
    return application;
  });
  setIntPosOfferData(updatedIntPosOffer);
  onClose();
};

//default export
const IntPosOffer = ({ item, onClose, profile }) => {
  const [otherPair, setOtherPair] = React.useState(null);
  const { intPosOfferData, setIntPosOfferData } = useIntPosOfferContext();
  logger.debug("Profile from intPosOffer", profile);
  useEffect(() => {
    //logger.debug("Item", item);
    if (profile.userType === "COMPANY") {
      cv.getStudentCV(item.studentID).then((response) => {
        setOtherPair(response.data.cv);
        //logger.debug("CV", response.data.cv);
      });
    } else {
      offer.getSpecificOffer(item.internshipOfferID).then((response) => {
        setOtherPair(response.data);
        //logger.debug("Offer", response.data);
      });
    }
  }, []);

  return (
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
        zIndex: 9999,
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
            display: "flex",
            flexDirection: "column",
            marginBottom: 2,
          }}
        >
          <CardContent>
            <Typography variant="h4" gutterBottom color="text.primary">
              {item.internshipTitle}
            </Typography>
            <Divider variant="middle" sx={{ my: 1 }} />
            {renderDetail("Student Name", item.studentName)}
            {renderDetail("Company Name", item.companyName)}
            {renderDetail("Status", item.status)}
            <Divider variant="middle" sx={{ my: 1 }} />
            <Typography variant="h6" gutterBottom color="text.primary">
              {profile.userType === "COMPANY"
                ? "Student CV Details"
                : "Internship Offer Details"}
            </Typography>
            {!otherPair ? (
              <Typography>Loading details...</Typography>
            ) : profile.userType === "COMPANY" ? (
              detailsSeeByCompany(otherPair)
            ) : (
              detailsSeeByStudent(otherPair)
            )}
          </CardContent>
        </Card>
        <Box sx={{ display: "flex", justifyContent: "space-around", mt: 2 }}>
          <Buttons
            userType={profile.userType}
            onClose={onClose}
            item={item}
            intPosOfferData={intPosOfferData}
            setIntPosOfferData={setIntPosOfferData}
          />
        </Box>
      </Box>
    </Box>
  );
};

const detailsSeeByCompany = (otherPair) => {
  return (
    <>
      {renderDetail("Certifications", otherPair.certifications.value)}
      {renderDetail("Education", otherPair.education.value)}
      {renderDetail("Project", otherPair.project.value)}
      {renderDetail("Skills", otherPair.skills.value)}
      {renderDetail("Spoken Languages", otherPair.spokenLanguages.value)}
      {renderDetail("Work Experiences", otherPair.workExperiences.value)}
    </>
  );
};

const detailsSeeByStudent = (otherPair) => {
  return (
    <>
      {renderDetail("Company Name", otherPair.companyName)}
      {renderDetail("Required skills", otherPair.requiredSkills)}
      {renderDetail("Location", otherPair.location)}
      {renderDetail("Duration", otherPair.duration)}
      {renderDetail("Compensation", otherPair.compensation)}
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
        sx={{ color: "text.primary", fontWeight: "bold" }}
      >
        {label}:
      </Typography>
      {" " + value}
    </Typography>
  );
};

const Buttons = ({
  userType,
  onClose,
  item,
  intPosOfferData,
  setIntPosOfferData,
}) => {
  const buttonStyles = {
    minWidth: "120px",
    padding: "8px 24px",
    transition: "all 0.2s ease-in-out",
  };

  if (userType === "STUDENT" && item.status === "pending") {
    return (
      <>
        <Button
          variant="contained"
          onClick={() =>
            onReject(item, onClose, intPosOfferData, setIntPosOfferData)
          }
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
          Reject
        </Button>
        <Button
          variant="contained"
          onClick={onClose}
          sx={{
            ...buttonStyles,
            backgroundColor: "#ffffff",
            color: "#000000",
            "&:hover": {
              backgroundColor: "#f5f5f5",
              transform: "scale(1.05)",
              boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
            },
          }}
        >
          Close
        </Button>
        <Button
          variant="contained"
          onClick={() =>
            onAccept(item, onClose, intPosOfferData, setIntPosOfferData)
          }
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
          Accept
        </Button>
      </>
    );
  } else {
    return (
      <>
        <Button
          variant="contained"
          onClick={onClose}
          sx={{
            ...buttonStyles,
            backgroundColor: "#ffffff",
            color: "#000000",
            "&:hover": {
              backgroundColor: "#f5f5f5",
              transform: "scale(1.05)",
              boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
            },
          }}
        >
          Close
        </Button>
      </>
    );
  }
};

export default IntPosOffer;
