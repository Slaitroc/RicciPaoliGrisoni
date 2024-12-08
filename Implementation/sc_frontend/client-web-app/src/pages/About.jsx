import React from "react";
import { styled } from "@mui/system";

const StyledAbout = styled("main")(({ theme }) => ({
  padding: theme.spacing(3),
}));

const About = () => {
  return <StyledAbout>About</StyledAbout>;
};

export default About;
