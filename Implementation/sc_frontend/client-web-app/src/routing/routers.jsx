import React from "react";
import { createBrowserRouter } from "react-router-dom";
import RouteProtector from "./RouteProtector";
import SCSignInSide from "../pages/SCSignInSide";
import SCSignUp from "../pages/SCSignUp";
import About from "../pages/About";
import Contact from "../pages/Contact";
import Main from "../pages/Main";
import Dashboard from "../pages/Dashboard";
import Home from "../pages/Home";

// Router Configuration
const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "about",
        element: <About />,
      },
      {
        path: "contacts",
        element: <Contact />,
      },
    ],
  },
  {
    path: "dashboard",
    element: (
      <RouteProtector equals={true} navigateTo="/signin">
        <Dashboard />
      </RouteProtector>
    ),
    children: [
      {
        path: "about",
        element: <About />,
      },
      {
        path: "contacts",
        element: <Contact />,
      },
    ],
  },
  {
    path: "signin",
    element: (
      <RouteProtector>
        <SCSignInSide />
      </RouteProtector>
    ),
  },
  {
    path: "signup",
    element: (
      <RouteProtector>
        <SCSignUp />
      </RouteProtector>
    ),
  },
]);

export default router;
