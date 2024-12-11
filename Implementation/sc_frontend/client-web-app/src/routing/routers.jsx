import React from "react";
import { createBrowserRouter } from "react-router-dom";
import RouteProtector from "./RouteProtector";
import SCSignInSide from "../pages/SCSignInSide";
import SCSignUp from "../pages/SCSignUp";
import About from "../pages/About";
import Contact from "../pages/Contact";
import Home from "../pages/Home";

// Router Configuration
const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
    children: [
      {
        path: "about",
        element: <About />,
      },
      {
        path: "contact",
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
