import * as React from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { red, blue, green, orange, purple } from "@mui/material/colors";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme }) => ({
  transition: theme.transitions.create("transform", {
    duration: theme.transitions.duration.shortest,
  }),
  variants: [
    {
      props: ({ expand }) => !expand,
      style: {
        transform: "rotate(0deg)",
      },
    },
    {
      props: ({ expand }) => !!expand,
      style: {
        transform: "rotate(180deg)",
      },
    },
  ],
}));

export default function RecommendationCard({ recommendation, offer }) {
  const [expanded, setExpanded] = React.useState(false);

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const getBackgroundColor = (letter) => {
    const colors = [red[500], blue[500], green[500], orange[500], purple[500]];
    return colors[letter.charCodeAt(0) % colors.length];
  };

  return (
    <Card
      sx={{
        // Added minHeight to prevent last card from being visible under the other RecommendationCards
        // Last card heigh is about 230px, if someone has a better solution please implement it
        minHeight: 235,
        width: 400,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <CardHeader
        avatar={
          <Avatar
            sx={{
              bgcolor: getBackgroundColor(recommendation.companyName.charAt(0)),
            }}
            aria-label="Company"
          >
            {recommendation.companyName.charAt(0)}
          </Avatar>
        }
        title={
          <span
            style={{
              fontWeight: "bold",
              display: "block",
              textAlign: "center",
            }}
          >
            {recommendation.companyName}
          </span>
        }
        subheader={
          <span style={{ display: "block", textAlign: "center" }}>
            {offer.title}
          </span>
        }
        sx={{
          "& .MuiCardHeader-title": {
            marginBottom: 1, // Slightly increase space between title and subheader
          },
        }}
      />
      {/* Adjusted padding for spacing */}
      <CardContent sx={{ paddingTop: 2, textAlign: "center" }}>
        <Typography
          variant="body2"
          sx={{
            color: "text.secondary",
            overflowWrap: "break-word",
            wordWrap: "break-word",
            hyphens: "auto",
          }}
        >
          {offer.description}
        </Typography>
      </CardContent>

      <CardActions
        disableSpacing
        sx={{ width: "100%", display: "flex", justifyContent: "center" }}
      >
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>

      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent
          sx={{
            textAlign: "center",
            overflowWrap: "break-word",
            wordWrap: "break-word",
            hyphens: "auto",
          }}
        >
          <Typography sx={{ marginBottom: 2, fontWeight: "bold" }}>
            Further information:
          </Typography>
          <Typography variant="body1" color="text.secondary">
            <Typography
              component="span"
              display="inline"
              variant="body2"
              sx={{ color: "text.primary", fontWeight: "bold" }}
            >
              Required skills:
            </Typography>
            {" " + offer.requiredSkills}
          </Typography>
          <Typography variant="body1" color="text.secondary">
            <Typography
              component="span"
              display="inline"
              variant="body2"
              sx={{ color: "text.primary", fontWeight: "bold" }}
            >
              Location:
            </Typography>
            {" " + offer.location}
          </Typography>
          <Typography variant="body1" color="text.secondary">
            <Typography
              component="span"
              display="inline"
              variant="body2"
              sx={{ color: "text.primary", fontWeight: "bold" }}
            >
              Duration:
            </Typography>
            {" " + offer.duration}
          </Typography>
          <Typography variant="body1" color="text.secondary">
            <Typography
              component="span"
              display="inline"
              variant="body2"
              sx={{ color: "text.primary", fontWeight: "bold" }}
            >
              Compensation:
            </Typography>
            {" " + offer.compensation}
          </Typography>
          <Typography variant="body1" color="text.secondary">
            <Typography
              component="span"
              display="inline"
              variant="body2"
              sx={{ color: "text.primary", fontWeight: "bold" }}
            >
              Affinity Score:
            </Typography>
            {" " + (recommendation.score / 2) * 100 + "%"}
          </Typography>
        </CardContent>
      </Collapse>
    </Card>
  );
}
