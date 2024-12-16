import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Grid2";
import Typography from "@mui/material/Typography";
import { styled } from "@mui/material/styles";
import { v4 as uuidv4 } from "uuid";
import * as SCIcons from "../Shared/SCIcons";

const cardData = [
  {
    key: uuidv4(),
    icon: <SCIcons.SCMailIcon />,
    tag: "contacts",
    title: "Email",
    link: "mailto:contact@studentandcompanies.com",
    description: "Send an email",
  },
  {
    key: uuidv4(),
    icon: <SCIcons.SCLinkedInIcon />,
    tag: "contacts",
    title: "Linkedin",
    link: "https://www.linkedin.com",
    description: "LinkedIn profile",
  },
  {
    key: uuidv4(),
    icon: <SCIcons.SCInstagramIcon />,
    tag: "contacts",
    title: "Instagram",
    link: "https://www.instagram.com",
    description: "Instagram profile",
  },
  {
    key: uuidv4(),
    icon: <SCIcons.SCFacebookIcon />,
    tag: "contacts",
    title: "Facebook",
    link: "https://www.facebook.com",
    description: "Facebook profile",
  },
  {
    key: uuidv4(),
    icon: <SCIcons.SCXIcon />,
    tag: "contacts",
    title: "X",
    link: "https://www.X.com",
    description: "X profile",
  },
];

const StyledCard = styled(Card)(({ theme }) => ({
  display: "flex",
  //flexDirection: "column",
  padding: 0,
  height: "100%",
  backgroundColor: (theme.vars || theme).palette.background.paper,
  "&:hover": {
    backgroundColor: "transparent",
    cursor: "pointer",
  },
  "&:focus-visible": {
    outline: "3px solid",
    outlineColor: "hsla(210, 98%, 48%, 0.5)",
    outlineOffset: "2px",
  },
}));

const StyledCardContent = styled(CardContent)({
  display: "flex",
  flexDirection: "column",
  gap: 4,
  padding: 16,
  flexGrow: 1,
  "&:last-child": {
    paddingBottom: 16,
  },
});

const StyledTypography = styled(Typography)({
  display: "-webkit-box",
  WebkitBoxOrient: "vertical",
  WebkitLineClamp: 2,
  overflow: "hidden",
  textOverflow: "ellipsis",
});

export default function SCContacts() {
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
    <Box sx={{ display: "flex", flexDirection: "column", gap: 4 }}>
      <div>
        <Typography variant="h1" gutterBottom>
          Contacts
        </Typography>
      </div>
      <Grid container spacing={2} columns={12}>
        {cardData.map((t, index) => {
          const component = (
            <>
              <Grid key={t.key} size={{ xs: 12, md: 6 }}>
                <StyledCard
                  variant="outlined"
                  onFocus={() => handleFocus(t.key)}
                  onBlur={() => handleBlur(t.key)}
                  tabIndex={index}
                  className={focusedCardIndex === t.key ? "Mui-focused" : ""}
                >
                  <StyledCardContent>
                    <Typography gutterBottom variant="caption" component="div">
                      {t.icon}
                    </Typography>
                    <Typography gutterBottom variant="h6" component="div">
                      {t.title}
                    </Typography>
                    <StyledTypography
                      variant="body2"
                      color="text.secondary"
                      gutterBottom
                    >
                      <a href={t.link} target="_blank">
                        {t.description}
                      </a>
                    </StyledTypography>
                  </StyledCardContent>
                </StyledCard>
              </Grid>
            </>
          );
          return component;
        })}
      </Grid>
    </Box>
  );
}
