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
import Settings from "../pages/Settings";
import Feedback from "../pages/Feedback";
import CV from "../pages/CV";
import Overview from "../pages/Overview";
import Applications from "../pages/Applications";
import Interviews from "../pages/Interviews";
import Recommendations from "../pages/Recommandations";
import Communications from "../pages/Communications";
import ConfInternships from "../pages/ConfInternships";
import University from "../pages/University";
import InternshipOffers from "../pages/InternshipOffers";
import Profile from "../pages/Profile";
import Account from "../pages/Account";
import SwipeManager from "../pages/SwipeManager";
// Router Configurations
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
        path: "",
        element: <Overview />,
      },
      {
        path: "about",
        element: <About />,
      },
      {
        path: "contacts",
        element: <Contact />,
      },
      {
        path: "settings",
        element: <Settings />,
      },
      {
        path: "feedback",
        element: <Feedback />,
      },
      {
        path: "cv",
        element: <CV />,
      },
      {
        path: "university",
        element: <University />,
      },
      {
        path: "internship-offers",
        element: <InternshipOffers />,
      },
      {
        path: "applications",
        element: <Applications />,
      },
      {
        path: "recommendations",
        element: <Recommendations />,
      },
      {
        path: "interviews",
        element: <Interviews />,
      },
      {
        path: "confirmed-internships",
        element: <ConfInternships />,
      },
      {
        path: "communications",
        element: <Communications />,
      },
      {
        path: "profile",
        element: <Profile />,
      },
      {
        path: "account",
        element: <Account />,
      },
      {
        path: "swipe-card",
        element: <SwipeManager />,
      }
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
