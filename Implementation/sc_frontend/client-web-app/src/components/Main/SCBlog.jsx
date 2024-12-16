import * as React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import SCAppAppBar from "./SCAppAppBar";
import SCFooter from "./SCFooter";
import SCAppTheme from "../Shared/SCAppTheme";
import { Outlet } from "react-router-dom";

export default function Blog(props) {
  return (
    <SCAppTheme {...props}>
      <CssBaseline enableColorScheme />
      <SCAppAppBar />
      <Container
        maxWidth="lg"
        component="main"
        sx={{ display: "flex", flexDirection: "column", marginTop: 16, marginBottom: 2, gap: 4 }}
      >
        <Outlet />
      </Container>
      <SCFooter />
    </SCAppTheme>
  );
}
