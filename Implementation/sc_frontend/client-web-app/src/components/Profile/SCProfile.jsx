import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Grid from "@mui/material/Grid2";
import Typography from "@mui/material/Typography";
import { styled } from "@mui/material/styles";
import { v4 as uuidv4 } from "uuid";
import { useGlobalContext } from "../../global/GlobalContext";
import * as account from "../../api-calls/api-wrappers/account-wrapper/account.js";
import * as logger from "../../logger/logger";
const StyledCard = styled(Card)(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
  padding: 0,
  height: "100%",
  backgroundColor: (theme.vars || theme).palette.background.paper,
  transition: "background-color 0.3s ease",
  "&:hover, &:focus-visible": {
    backgroundColor: "rgba(255, 255, 255, 0.8)", // Lower opacity white background
    cursor: "pointer",
    ".MuiTypography-h6, .MuiTypography-body2": {
      color: "black", // Change text color to black on hover/focus
    },
    outline: "3px solid",
    outlineColor: "hsla(210, 98%, 48%, 0.5)",
    outlineOffset: "2px",
  },
}));

const StyledCardContent = styled(CardContent)({
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  textAlign: "center",
  padding: 16,
  paddingTop: 0,
  flexGrow: 1,
  "&:last-child": {
    paddingBottom: 1,
  },
});

const StyledTypography = styled(Typography)({
  display: "-webkit-box",
  WebkitBoxOrient: "vertical",
  WebkitLineClamp: 2,
  overflow: "hidden",
  textOverflow: "ellipsis",
});

export default function SCProfile() {
  const [uniName, setUniName] = React.useState("N/A");
  const { profile } = useGlobalContext();

  //Only when the component mounts, print the profile object and get the university name if the user is a student
  //React.useEffect(() => {
  //  console.log("Profile page - profile:", profile);
  //  account.getUniversities().then((response) => {
  //    const uniTable = response.table;
  //    if (!uniTable || profile.userType != "STUDENT" ) return;
  //    for (const [name, vat] of Object.entries(uniTable)) {
  //      if (vat === profile.uniVat) {
  //        setUniName(name);
  //        break;
  //      }
  //    }
  //  });
  //}, []);

  React.useEffect(() => {
    setUniName("N/A");
    console.log("Profile page - profile:", profile);
    account.getUniversitiesv2().then((response) => {
      logger.debug("getUniversitiesv2 response:", response);
      if (response.success) {
        response.names.forEach((name) => {
          if (profile.uniVat === response.table[name]) {
            setUniName(name);
          }
        });
      }
    });
  }, []);

  const profileData = [
    //mandatory fields
    {
      key: uuidv4(),
      title: "Name",
      description:
        profile.userType === "COMPANY" && profile.name === "ferrari"
          ? "FERRARI🏎️"
          : profile.name,
    },
    {
      key: uuidv4(),
      title: "Email",
      description: profile.email || "N/A",
    },
    {
      key: uuidv4(),
      title: "Country",
      description: profile.country || "N/A",
    },
    {
      key: uuidv4(),
      title: "User Type",
      description: profile.userType || "N/A",
    },
    {
      key: uuidv4(),
      title: "Account Verified",
      description: profile.validate ? "Yes" : "No",
    },
    //additional fields based on userType
    ...(profile.userType === "COMPANY"
      ? [
          {
            key: uuidv4(),
            title: "Location",
            description: profile.location || "N/A",
          },
          {
            key: uuidv4(),
            title: "VAT Number",
            description: profile.vatNumber || "N/A",
          },
        ]
      : profile.userType === "UNIVERSITY"
      ? [
          {
            key: uuidv4(),
            title: "Location",
            description: profile.location || "N/A",
          },
          {
            key: uuidv4(),
            title: "VAT Number",
            description: profile.vatNumber || "N/A",
          },
          {
            key: uuidv4(),
            title: "University Description",
            description: profile.uniDesc || "N/A",
          },
        ]
      : profile.userType === "STUDENT"
      ? [
          {
            key: uuidv4(),
            title: "Birthdate",
            description: profile.birthDate || "N/A",
          },
          {
            key: uuidv4(),
            title: "University",
            description: uniName,
          },
        ]
      : []), //if no userType is found, return an empty array (no additional fields)
  ];

  const [focusedCardIndex, setFocusedCardIndex] = React.useState(null);

  const handleFocus = (index) => {
    setFocusedCardIndex(index);
  };

  const handleBlur = (index) => {
    setFocusedCardIndex(index);
  };

  const handleClick = () => {
    // console.info("You clicked the filter chip.");
  };

  return (
    <Box
      sx={{ display: "flex", flexDirection: "column", gap: 4, width: "100%" }}
    >
      <div>
        <Typography variant="h1" gutterBottom>
          Profile
        </Typography>
      </div>
      <Grid container spacing={2} columns={12}>
        {profileData.map((t, index) => {
          return (
            <Grid key={t.key} size={{ xs: 12, md: 12 }}>
              <StyledCard
                variant="outlined"
                onFocus={() => handleFocus(t.key)}
                onBlur={() => handleBlur(t.key)}
                tabIndex={index}
                className={focusedCardIndex === t.key ? "Mui-focused" : ""}
                sx={{ width: "100%" }}
              >
                <CardMedia
                  sx={{
                    borderBottom: "1px solid",
                    borderColor: "divider",
                  }}
                />
                <StyledCardContent>
                  <Typography gutterBottom variant="h6" component="div">
                    {t.title}
                  </Typography>
                  <StyledTypography
                    variant="body2"
                    color="text.secondary"
                    gutterBottom
                  >
                    {t.description}
                  </StyledTypography>
                </StyledCardContent>
              </StyledCard>
            </Grid>
          );
        })}
      </Grid>
    </Box>
  );
}
