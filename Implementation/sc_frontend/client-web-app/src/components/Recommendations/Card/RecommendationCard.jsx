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
import { useGlobalContext } from "../../../global/GlobalContext";
import * as logger from "../../../logger/logger";

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

export default function RecommendationCard({ recommendation, otherPair }) {
  const { profile } = useGlobalContext();
  const [expanded, setExpanded] = React.useState(false);
  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  const getBackgroundColor = (letter) => {
    const colors = [red[500], blue[500], green[500], orange[500], purple[500]];
    return colors[letter.charCodeAt(0) % colors.length];
  };

  const cardSeenByStudent = () => {
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
                bgcolor: getBackgroundColor(
                  recommendation.companyName.charAt(0)
                ),
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
              {otherPair.title}
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
            {otherPair.description}
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
            {renderDetail("Required skills", otherPair.requiredSkills)}
            {renderDetail("Location", otherPair.location)}
            {renderDetail("Duration", otherPair.duration)}
            {renderDetail("Compensation", otherPair.compensation)}
            {renderDetail(
              "Affinity Score",
              (recommendation.score / 2) * 100 + "%"
            )}
          </CardContent>
        </Collapse>
      </Card>
    );
  };

  const cardSeenByCompany = () => {
    //logger.debug("Other pair: ", otherPair);
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
                bgcolor: getBackgroundColor(
                  otherPair.studentName.value.charAt(0)
                ),
              }}
              aria-label="Company"
            >
              {otherPair.studentName.value.charAt(0)}
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
              {otherPair.studentName.value}
            </span>
          }
          subheader={
            <span style={{ display: "block", textAlign: "center" }}>
              {recommendation.internshipOfferTitle}
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
            {otherPair.contacts.value}
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
            {renderDetail("Certifications", otherPair.certifications.value)}
            {renderDetail("Education", otherPair.education.value)}
            {renderDetail("Project", otherPair.project.value)}
            {renderDetail("Skills", otherPair.skills.value)}
            {renderDetail("Spoken Languages", otherPair.spokenLanguages.value)}
            {renderDetail("Work Experiences", otherPair.workExperiences.value)}
            {renderDetail(
              "Affinity Score",
              (recommendation.score / 2) * 100 + "%"
            )}
          </CardContent>
        </Collapse>
      </Card>
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

  if (profile.userType === "STUDENT") {
    return cardSeenByStudent();
  } else {
    return cardSeenByCompany();
  }
}
