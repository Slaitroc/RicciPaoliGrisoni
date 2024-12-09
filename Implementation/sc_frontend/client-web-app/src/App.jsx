import DashboardIcon from "@mui/icons-material/Dashboard";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { createTheme } from "@mui/material/styles";
import { RouterProvider } from "react-router-dom";
import router from "./routers";
import { useEffect, useState } from "react";
import SCDashboard from "./components/Home/SCDashboard";
import { fakeFetchUserData, fakeLogin } from "./fake_backend/fakeBackend";

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
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const login = async (username, password) => {
    setLoading(true);
    setError(null);
    try {
      const { token, userId } = await fakeLogin(username, password);
      localStorage.setItem("token", token);
      localStorage.setItem("userId", userId);
      setIsAuthenticated(true);
      await fakeFetchUserData(userId);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    setIsAuthenticated("false");
    setProfile("null");
  };

  const fetchProfile = async (userId) => {
    setLoading(true);
    try {
      const userProfile = await fetchProfile(userId);
      setProfile(userProfile);
    } catch {
      setError("Failed To Fetch Profile");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");
    if (token && userId) {
      setIsAuthenticated(true);
      fetchProfile(userId);
    }
  }, []);

  //TODO creare i componenti visivi per testare il codice

  return <RouterProvider router={router} />;
}
export default App;
