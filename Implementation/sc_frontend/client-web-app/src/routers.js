import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import About from "./pages/About";
import Contact from "./pages/Contact";
import SCSignInSide from "./pages/SCSignInSide";
import SCSignUp from "./pages/SCSignUp";

const router = createBrowserRouter([
  {
    path: "/", // Rotte che usano DashboardLayout
    Component: App,
    children: [
      {
        path: "/about",
        Component: About,
      },
      {
        path: "/contact",
        Component: Contact,
      },
    ],
  },
  {
    path: "/signin", // Rotte che non usano DashboardLayout
    Component: SCSignInSide,
  },
  {
    path: "/signup", // Rotte che non usano DashboardLayout
    Component: SCSignUp,
  },
]);

export default router;
