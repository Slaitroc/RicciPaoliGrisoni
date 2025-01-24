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
    title: "New Reccomendations",
    description: "",
  },
  {
    key: uuidv4(),
    title: "New Internship Offers",
    description: "",
  },
  {
    key: uuidv4(),
    title: "Match Updates",
    description: "",
  },
  {
    key: uuidv4(),
    title: "New Internship Offers",
    description: "",
  },
  {
    key: uuidv4(),
    title: "Ongoing Interview",
    description: "",
  },
];

export default function SCOverview() {
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
          Overview
        </Typography>
      </div>
    </Box>
  );
}
