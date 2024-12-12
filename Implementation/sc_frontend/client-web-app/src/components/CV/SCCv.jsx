import * as React from "react";
import { Box, Container, Input, TextField } from "@mui/material";
import Typography from "@mui/material/Typography";
import { v4 as uuidv4 } from "uuid";

export default function SCCv() {
  return (
    <Box display="flex" flexDirection="column">
      <Input multiline></Input>
    </Box>
  );
}
