import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Grid from "@mui/material/Grid2";
import Typography from "@mui/material/Typography";
import { styled } from "@mui/material/styles";
import { v4 as uuidv4 } from "uuid";

const cardData = [
  {
    key: uuidv4(),
    image:
      "https://plus.unsplash.com/premium_photo-1661573729122-6619f62ef0ea?q=80&w=1915&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    tag: "AboutUs",
    title: "Our Purpose",
    description:
      "The purpose of the Student&Company (S&C) Platform is to create a system that allows Students to find Internships to enhance their education and improve their curriculum, while allowing Companies to find suitable candidates for their internship programs. All of this is done in a simple and efficient way by providing a series of tools to help both parties in the process.\n\nS&C will support the entire lifecycle of the Internship process for both Students and Companies: from the initial matchmaking that can be done automatically by the system through a proprietary Recommendation Process, or obtained by a Student with a Spontaneous Application to a specific internship offer, to the final selection process done through structured interviews created and submitted by Companies directly on the platform.\n\nIn the meantime, Student&Company will also provide a series of Suggestions to improve CVs published by Students and internship offers published by Companies. The platform will also allow the Universities of Students who are actually doing an internship to monitor the progress of such activities and handle any Complaints if necessary, even by terminating the internship if no other solution to the problem can be found.",
  },
];

const StyledCard = styled(Card)(({ theme }) => ({
  display: "flex",
  flexDirection: "column",
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

export default function SCAbout() {
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
          About
        </Typography>
        <Typography>Our History</Typography>
      </div>
      <Grid container spacing={2} columns={12}>
        {cardData.map((t, index) => {
          return (
            <Grid key={t.key} size={{ xs: 12, md: 6 }}>
              <StyledCard
                variant="outlined"
                onFocus={() => handleFocus(t.key)}
                onBlur={() => handleBlur(t.key)}
                tabIndex={index}
                className={focusedCardIndex === t.key ? "Mui-focused" : ""}
              >
                <CardMedia
                  component="img"
                  alt="green iguana"
                  image={t.image}
                  sx={{
                    aspectRatio: "16 / 9",
                    borderBottom: "1px solid",
                    borderColor: "divider",
                  }}
                />
                <StyledCardContent>
                  <Typography gutterBottom variant="caption" component="div">
                    {t.tag}
                  </Typography>
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
