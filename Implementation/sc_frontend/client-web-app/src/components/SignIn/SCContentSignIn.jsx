import * as React from "react";
import Box from "@mui/material/Box";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import { TEXT } from "../../constants/UIConstants";
import * as SCIcons from "../Shared/SCIcons";

const items = [
  {
    icon: (
      <SCIcons.SCSettingsSuggestRoundedIcon sx={{ color: "text.secondary" }} />
    ),
    title: `${TEXT.FULL_SIGN}`,
    description:
      "Our product effortlessly adjusts to your needs, boosting efficiency and simplifying your tasks.",
  },
  {
    icon: (
      <SCIcons.SCConstructionRoundedIcon sx={{ color: "text.secondary" }} />
    ),
    title: "Adaptable performance",
    description:
      "Experience unmatched durability that goes above and beyond with lasting investment.",
  },
  {
    icon: <SCIcons.SCThumbUpAltRoundedIcon sx={{ color: "text.secondary" }} />,
    title: "Great user experience",
    description:
      "Our product effortlessly adjusts to your needs, boosting efficiency and simplifying your tasks.",
  },
  {
    icon: <SCIcons.SCAutoFixHighRoundedIcon sx={{ color: "text.secondary" }} />,
    title: "Innovative functionality",
    description:
      "Stay ahead with features that set new standards, addressing your evolving needs better than the rest.",
  },
];

export default function SCContentSignIn() {
  return (
    <Stack
      sx={{
        flexDirection: "column",
        alignSelf: "center",
        gap: 4,
        maxWidth: 450,
      }}
    >
      <Box sx={{ display: { xs: "none", md: "flex" } }}></Box>
      {items.map((item, index) => (
        <Stack key={index} direction="row" sx={{ gap: 2 }}>
          {item.icon}
          <div>
            <Typography gutterBottom sx={{ fontWeight: "medium" }}>
              {item.title}
            </Typography>
            <Typography variant="body2" sx={{ color: "text.secondary" }}>
              {item.description}
            </Typography>
          </div>
        </Stack>
      ))}
    </Stack>
  );
}
