import { styled } from "@mui/system";
import React from "react";

const StyledContact = styled("main")(({ theme }) => ({
  padding: theme.spacing(3),
}));

const Contact = () => {
  return <StyledContact>Contact</StyledContact>;
};

export default Contact;
