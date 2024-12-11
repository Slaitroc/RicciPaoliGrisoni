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
    title: "Profile info 1",
    description: "",
  },
  {
    key: uuidv4(),
    title: "Profile info 2",
    description: "",
  },
  {
    key: uuidv4(),
    title: "Profile info 3",
    description: "",
  },
  {
    key: uuidv4(),
    title: "Profile info 4",
    description: "",
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
        {cardData.map((t, index) => {
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
