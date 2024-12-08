import DashboardIcon from "@mui/icons-material/Dashboard";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { createTheme } from "@mui/material/styles";
import SCDashboard from "./components/SCDashboard";

const theme = createTheme({
  colorSchemes: {
    dark: true,
  },
});

const NAVIGATION = [
  {
    kind: "header",
    title: "Main Items",
  },
  {
    segment: "dashboard",
    title: "Dashboard",
    icon: <DashboardIcon />,
  },
  {
    segment: "about",
    title: "About",
    icon: <ShoppingCartIcon />,
  },
  {
    segment: "contact",
    title: "Contact",
    icon: <ShoppingCartIcon />,
  },
  {
    segment: "signin",
    title: "SignIn",
    icon: <ShoppingCartIcon />,
  },
  {
    segment: "signup",
    title: "SignUp",
    icon: <ShoppingCartIcon />,
  },
];

function App() {
  return <SCDashboard />;
}
export default App;
