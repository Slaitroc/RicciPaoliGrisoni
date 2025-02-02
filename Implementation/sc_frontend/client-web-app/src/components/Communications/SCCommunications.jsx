// SCCommunications.jsx
import * as React from "react";
import { Box, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { CommunicationsList } from "./CommunicationsList";
import { SCAddIcon } from "../Shared/SCIcons";
import * as internshipPositionOffer from "../../api-calls/api-wrappers/Interview/intPosOffer";
import { useGlobalContext } from "../../global/GlobalContext";
import { useCommunicationsContext } from "./CommunicationsContext";
import { log, focus } from "../../logger/logger";

// SCCommunications.jsx
export default function SCCommunications() {
  const { profile } = useGlobalContext();
  const navigate = useNavigate();
  const {
    setOpenAlert,
    setAlertMessage,
    setAlertSeverity,
    setInternshipPositionOffers,
  } = useCommunicationsContext();

  const handleClicked = async (type) => {
    try {
      const response =
        await internshipPositionOffer.getFormattedAcceptedInterviewPosOffers();
      log(response);
      if (!response.success) {
        focus("error");
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      } else {
        setOpenAlert(false);
        setInternshipPositionOffers(response.data); // Will be empty array if 204
        if (type === "communication") {
          navigate("new/communication");
        } else if (type === "complaint") {
          navigate("new/complaint");
        }
      }
    } catch (error) {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("Error fetching communications");
    }
  };

  log(profile);
  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        My Communications
      </Typography>

      {profile.userType !== "UNIVERSITY" && (
        <Box sx={{ mb: 3, display: "flex", gap: 2 }}>
          <Button
            variant="outlined"
            startIcon={<SCAddIcon />}
            onClick={() => handleClicked("communication")}
          >
            New Communication
          </Button>
          <Button
            variant="outlined"
            startIcon={<SCAddIcon />}
            onClick={() => handleClicked("complaint")}
          >
            New Complaint
          </Button>
        </Box>
      )}

      <CommunicationsList
        onItemClick={(communication) => onItemClicked(communication)}
      />
    </Box>
  );
}

// ________________________________OLD ONE________________________________
// import * as React from "react";
// import Box from "@mui/material/Box";
// import Card from "@mui/material/Card";
// import CardContent from "@mui/material/CardContent";
// import CardMedia from "@mui/material/CardMedia";
// import Grid from "@mui/material/Grid2";
// import Typography from "@mui/material/Typography";
// import { styled } from "@mui/material/styles";
// import { v4 as uuidv4 } from "uuid";
// import SCMessage from "./SCMessage";
// import { SCAddIcon, SCCollectionsBookmarkIcon } from "../Shared/SCIcons";
// import SCComplaintsList from "./SCComplaintsList";
// import { Button } from "@mui/material";

// const cardData = [
//   {
//     key: uuidv4(),
//     title: "Comm1",
//     description: "",
//   },
//   {
//     key: uuidv4(),
//     title: "Comm2",
//     description: "",
//   },
//   {
//     key: uuidv4(),
//     title: "Comm3",
//     description: "",
//   },
//   {
//     key: uuidv4(),
//     title: "Comm4",
//     description: "",
//   },
// ];

// const StyledCard = styled(Card)(({ theme }) => ({
//   display: "flex",
//   flexDirection: "column",
//   padding: 0,
//   height: "100%",
//   backgroundColor: (theme.vars || theme).palette.background.paper,
//   "&:hover": {
//     backgroundColor: "transparent",
//     cursor: "pointer",
//   },
//   "&:focus-visible": {
//     outline: "3px solid",
//     outlineColor: "hsla(210, 98%, 48%, 0.5)",
//     outlineOffset: "2px",
//   },
// }));

// const StyledCardContent = styled(CardContent)({
//   display: "flex",
//   flexDirection: "column",
//   padding: 16,
//   paddingTop: 0,
//   flexGrow: 1,
//   "&:last-child": {
//     paddingBottom: 1,
//   },
// });

// const StyledTypography = styled(Typography)({
//   display: "-webkit-box",
//   WebkitBoxOrient: "vertical",
//   WebkitLineClamp: 2,
//   overflow: "hidden",
//   textOverflow: "ellipsis",
// });

// export default function SCCommunications() {
//   const [focusedCardIndex, setFocusedCardIndex] = React.useState(null);

//   const handleFocus = (index) => {
//     setFocusedCardIndex(index);
//   };

//   const handleBlur = (index) => {
//     setFocusedCardIndex(index);
//   };

//   const handleClick = () => {
//     // console.info("You clicked the filter chip.");
//   };

//   return (
//     // <Box
//     //   sx={{ display: "flex", flexDirection: "column", gap: 4, width: "100%" }}
//     // >
//     //   <div>
//     //     <Typography variant="h1" gutterBottom>
//     //       Communications
//     //     </Typography>
//     //   </div>
//     //   <Grid container spacing={2} columns={12}>
//     //     {cardData.map((t, index) => {
//     //       return (
//     //         <Grid key={t.key} size={{ xs: 12, md: 12 }}>
//     //           <StyledCard
//     //             variant="outlined"
//     //             onFocus={() => handleFocus(t.key)}
//     //             onBlur={() => handleBlur(t.key)}
//     //             tabIndex={index}
//     //             className={focusedCardIndex === t.key ? "Mui-focused" : ""}
//     //             sx={{ width: "100%" }}
//     //           >
//     //             <CardMedia
//     //               sx={{
//     //                 borderBottom: "1px solid",
//     //                 borderColor: "divider",
//     //               }}
//     //             />
//     //             <StyledCardContent>
//     //               <Typography gutterBottom variant="h6" component="div">
//     //                 {t.title}
//     //               </Typography>
//     //               <StyledTypography
//     //                 variant="body2"
//     //                 color="text.secondary"
//     //                 gutterBottom
//     //               >
//     //                 {t.description}
//     //               </StyledTypography>
//     //             </StyledCardContent>
//     //           </StyledCard>
//     //         </Grid>
//     //       );
//     //     })}
//     //   </Grid>
//     // </Box>
//     <>
//       <Box padding={2} display="flex" flexDirection="row" gap={4}>
//         <Button variant="outlined" startIcon={<SCAddIcon />}>
//           Create New Commmunication
//         </Button>
//         <Button variant="outlined" startIcon={<SCAddIcon />}>
//           Create New Complaint
//         </Button>
//       </Box>
//       <SCComplaintsList></SCComplaintsList>
//     </>
//   );
// }
