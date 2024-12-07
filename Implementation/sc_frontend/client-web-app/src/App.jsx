import { useEffect } from "react";
import SCPage from "./components/SCPage/SCPage";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    customIcon: {
      main: "#EEEEEE",
      secondary: "#1976d2",
    },
    primary: {
      main: "#1976d2", // Colore principale
    },
    secondary: {
      main: "#222831", // Colore secondario
    },
    background: {
      default: "#222831",
      paper: "#31363F",
    },
    text: {
      primary: "#EEEEEE",
    },
    shadows: [
      "none",
      "0px 4px 10px rgba(0, 0, 0, 0.6)", // Ombra personalizzata
      "0px 8px 15px rgba(0, 0, 0, 0.7)", // Ombra evidente per AppBar e Drawer
    ],
    mixins: {
      toolbar: {
        minHeight: 56, // Altezza predefinita
        "@media (min-width:600px)": {
          minHeight: 64, // Altezza per schermi grandi
        },
      },
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
      <SCPage />
    </ThemeProvider>
  );
}
export default App;
