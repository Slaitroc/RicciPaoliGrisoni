import * as React from "react";
import PropTypes from "prop-types";
import Avatar from "@mui/material/Avatar";
import AvatarGroup from "@mui/material/AvatarGroup";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Chip from "@mui/material/Chip";
import Grid from "@mui/material/Grid2";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import FormControl from "@mui/material/FormControl";
import InputAdornment from "@mui/material/InputAdornment";
import OutlinedInput from "@mui/material/OutlinedInput";
import { styled } from "@mui/material/styles";
import SearchRoundedIcon from "@mui/icons-material/SearchRounded";
import RssFeedRoundedIcon from "@mui/icons-material/RssFeedRounded";
import { v4 as uuidv4 } from "uuid";

const cardData = [
  {
    key: uuidv4(),
    image: "https://picsum.photos/800/450?random=1",
    tag: "UserScenario",
    title: "Student Sign-up",
    description:
      "Mario Rossi is a student that want to improve his ability and education by doing an internship before graduating. He opens the SC platform and select “Student SignUp”. He proved the required personal information such as his name, surname, date of birth, an email, and a password that he will use as login credential. He also select from the list of available university the university he goes to. If the email address has never been used on the site, Mario will receive an email for confirming the mail address and the registration of the account. Once the registration is confirmed by Mario, the account is created. If the email address is already in use, the platform will show an error that ask to insert a new email address.",
  },
  {
    key: uuidv4(),
    image: "https://picsum.photos/800/450?random=2",
    tag: "UserScenario",
    title: "Company Sign-up",
    description:
      "FastRedCar SPA is a world-leading car company that aims to launch an internship program to train new mechanical engineers in their final year of a Bachelor’s or Master’s degree. The company opens the S&C platform and selects “Company SignUp” where they provide the required information such as the company name, company headquarters address, company VAT number, and also an email address and a password that will be used as login credentials.\n\nIf the VAT number has never been used on the site, FastRedCar SPA will receive an email for confirming the mail address and the registration of the account. Once the registration is confirmed, the account is created. If the VAT number is already in use on S&C, an error will be shown indicating that the company already has an account registered on the platform.",
  },
  {
    key: uuidv4(),
    image: "https://picsum.photos/800/450?random=3",
    tag: "UserScenario",
    title: "University Sign-up",
    description:
      "The Technical University of Milan is a prestigious university that wants its students to complete an internship before graduating, believing this experience will enhance their skills and knowledge. The university opens the SC platform and selects “University SignUp” where they provide the required information such as the university name, the university description, the university VAT number, the name of the university office that will manage the internship program, and also an email address and a password that will be used as login credentials.\n\nIf the VAT number has never been used on the site, the Technical University of Milan will receive an email for confirming the mail address and the registration of the account. Once the registration is confirmed, the account is created. If the VAT number is already in use, the platform will show an error indicating the university is already registered on the platform.",
  },
  {
    key: uuidv4(),
    image: "https://picsum.photos/800/450?random=45",
    tag: "UserScenario",
    title: "User Login",
    description:
      "A platform user that has already registered an account can log in by providing the email and password used during the registration. If the email and password are correct, matching an entry in the platform DB, the user is redirected to the platform home page. If the email or password are incorrect, the platform will show an error message indicating that the login credentials are wrong.",
  },
  {
    key: uuidv4(),
    image: "https://picsum.photos/800/450?random=6",
    tag: "UserScenario",
    title: "Student Load Curriculum",
    description:
      "Stefano is a student who has already registered an account on SC and wants to complete his profile by uploading his CV. From the platform’s homepage, he clicks on the “Upload CV” button. He is then redirected to a page where he can enter his curriculum information, including his current level of education, languages he knows, technical skills, and, optionally, details about past work experience along with contact information for previous employers. He also adds a photo of himself, a brief description of his interests and hobbies and, as soon as he clicks on the “Submit CV” button, the platform elaborates it and tries to find some matching internships based on the given information.\n\nA list of five different internships, for which Stefano is a match, is shown to the student in the platform’s homepage where he can decide to apply for one of them, notifying the company. While computing the matching, the platform also provides Stefano with some suggestions on how to improve his CV and matching probability, based on a grammar and lexical analysis and a direct comparison of Stefano’s CV with other similar candidates.",
  },
  {
    key: uuidv4(),
    image: "https://picsum.photos/800/450?random=4",
    tag: "UserScenario",
    title: "Company Submit an Internship Insertion",
    description:
      "AnanasPhone is a major tech company, specialized in the production of smartphones and tablets, that has an account on the S&C site. The company wants to create an internship program aimed at software engineering students in their final year of a Master’s degree. A Human Resource employee opens the S&C platform and selects “My Internship” where a list of all the internships already present on S&C is shown. Here, they click on “Insert Internship” where they provide the required information such as the internship title, the internship description, the start date and duration, the office address, a list of the required skills students need to have in order to be considered for the internship, and possibly, a list of benefits offered to the future intern.Once the internship is created, by clicking on the “Submit Internship” button, the platform starts the recommendation process to match the internship with all the students compatible with such an opportunity, based on the given information of both parties. The platform will also provide AnanasPhone with some suggestions on how to improve the internship description and matching probability, based on grammar and lexical analyses and a direct comparison of AnanasPhone’s internship proposal with other similar companies.",
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

function Author({ authors }) {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "row",
        gap: 2,
        alignItems: "center",
        justifyContent: "space-between",
        padding: "16px",
      }}
    >
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          gap: 1,
          alignItems: "center",
        }}
      >
        <AvatarGroup max={3}>
          {authors.map((author, index) => (
            <Avatar
              key={index}
              alt={author.name}
              src={author.avatar}
              sx={{ width: 24, height: 24 }}
            />
          ))}
        </AvatarGroup>
        <Typography variant="caption">
          {authors.map((author) => author.name).join(", ")}
        </Typography>
      </Box>
      <Typography variant="caption">July 14, 2021</Typography>
    </Box>
  );
}

Author.propTypes = {
  authors: PropTypes.arrayOf(
    PropTypes.shape({
      avatar: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export function Search() {
  return (
    <FormControl sx={{ width: { xs: "100%", md: "25ch" } }} variant="outlined">
      <OutlinedInput
        size="small"
        id="search"
        placeholder="Search…"
        sx={{ flexGrow: 1 }}
        startAdornment={
          <InputAdornment position="start" sx={{ color: "text.primary" }}>
            <SearchRoundedIcon fontSize="small" />
          </InputAdornment>
        }
        inputProps={{
          "aria-label": "search",
        }}
      />
    </FormControl>
  );
}

export default function MainContent() {
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
          Home
        </Typography>
        <Typography>Stay in the loop with the latest news</Typography>
      </div>
      {/* <Box
        sx={{
          display: { xs: "flex", sm: "none" },
          flexDirection: "row",
          gap: 1,
          width: { xs: "100%", md: "fit-content" },
          overflow: "auto",
        }}
      >
        <Search />
      </Box> */}
      {/* <Box
        sx={{
          display: "flex",
          flexDirection: { xs: "column-reverse", md: "row" },
          width: "100%",
          justifyContent: "space-between",
          alignItems: { xs: "start", md: "center" },
          gap: 4,
          overflow: "auto",
        }}
      >
        <Box
          sx={{
            display: "inline-flex",
            flexDirection: "row",
            gap: 3,
            overflow: "auto",
          }}
        >
          <Chip onClick={handleClick} size="medium" label="All categories" />
          <Chip
            onClick={handleClick}
            size="medium"
            label="User Scenarios"
            sx={{
              backgroundColor: "transparent",
              border: "none",
            }}
          />
          <Chip
            onClick={handleClick}
            size="medium"
            label="Companies"
            sx={{
              backgroundColor: "transparent",
              border: "none",
            }}
          />
          <Chip
            onClick={handleClick}
            size="medium"
            label="Students"
            sx={{
              backgroundColor: "transparent",
              border: "none",
            }}
          />
          <Chip
            onClick={handleClick}
            size="medium"
            label="Universities"
            sx={{
              backgroundColor: "transparent",
              border: "none",
            }}
          />
        </Box>
        <Box
          sx={{
            display: { xs: "none", sm: "flex" },
            flexDirection: "row",
            gap: 1,
            width: { xs: "100%", md: "fit-content" },
            overflow: "auto",
          }}
        >
          <Search />
          <IconButton size="small" aria-label="RSS feed">
            <RssFeedRoundedIcon />
          </IconButton>
        </Box>
      </Box> */}
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
