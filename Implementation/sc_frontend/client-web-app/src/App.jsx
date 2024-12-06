import { useEffect } from "react";
import SCNavbar from "./components/SCNavbar/SCNavbar";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    primary: {
      main: "#1976d2", // Colore principale
    },
    secondary: {
      main: "#dc004e", // Colore secondario
    },
    background: {
      default: "#222831",
      paper: "#31363F",
    },
    text: {
      primary: "#EEEEEE",
    },
  },
});

function App() {
  useEffect(() => {
    document.body.style.backgroundColor = theme.palette.background.default;
    document.body.style.color = theme.palette.text.primary;
  });
  return (
    <ThemeProvider theme={theme}>
      <SCNavbar />
    </ThemeProvider>
  );
}
export default App;
