import { ConfirmEmail } from "../pages/ConfirmEmail";
import { RouteBase } from "../pages/RouteBase";
import { SCEditCv } from "../components/CV/SCEditCV";
import { SCBrowseInternshipPreview } from "../components/BrowseInternshipOffers/SCBrowseInternshipPreview";
import SCBrowseInternshipOffers from "../components/BrowseInternshipOffers/SCBrowseInternshipOffers";
import { SCSignUp } from "../components/SignUp/SCSignUp";
import { createBrowserRouter } from "react-router-dom";
import { SignUp } from "../pages/SignUp";
import { SCUserCreation } from "../components/SignUp/SCUserCreation";
import SCApplicationPreview from "../components/Applications/SCApplicationPreview";
import SCApplication from "../components/Applications/SCApplication";
import React from "react";
import EmailRouteProtector from "./EmailRouteProtector";
import AuthRouteProtector from "./AuthRouteProtector";
import UserCreationRouteProtector from "./UserCreationRouteProtector";
import SCSignInSide from "../pages/SCSignInSide";
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
import SwipePage from "../pages/SwipePage";
import VerifyEmail from "../pages/VerifyEmail";
import SCCv from "../components/CV/SCCV";
import SCIntOffers from "../components/InternshipOffers/SCIntOffer";
import SCIntOffersPreview from "../components/InternshipOffers/SCIntOffersPreview";
import BrowseInternshipOffers from "../pages/BrowseInternshipOffers";
// Router Configurations

const router = createBrowserRouter(
  [
    {
      path: "/",
      element: <RouteBase />,
      children: [
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
          path: "confirm-email",
          element: (
            <AuthRouteProtector>
              <EmailRouteProtector invertBehavior={true}>
                <ConfirmEmail />
              </EmailRouteProtector>
            </AuthRouteProtector>
          ),
        },
        {
          path: "email-verified",
          element: <VerifyEmail />,
        },
        {
          path: "dashboard",
          element: (
            <AuthRouteProtector>
              <UserCreationRouteProtector>
                <EmailRouteProtector>
                  <Dashboard />
                </EmailRouteProtector>
              </UserCreationRouteProtector>
            </AuthRouteProtector>
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
              children: [
                {
                  path: "",
                  element: <SCCv />,
                },
                {
                  path: "edit",
                  element: <SCEditCv />,
                },
              ],
            },
            {
              path: "university",
              element: <University />,
            },
            {
              path: "internship-offers",
              element: <InternshipOffers />,
              children: [
                {
                  path: "",
                  element: <SCIntOffersPreview />,
                },
                {
                  path: "internship-detail",
                  element: <SCIntOffers />,
                },
              ],
            },
            {
              path: "browse-internship-offers",
              element: <BrowseInternshipOffers />,
              children: [
                {
                  path: "",
                  element: <SCBrowseInternshipPreview />,
                },
                {
                  path: "internship-detail",
                  element: <SCBrowseInternshipOffers />,
                },
              ],
            },
            {
              path: "applications",
              element: <Applications />,
              children: [
                {
                  path: "",
                  element: <SCApplicationPreview />,
                },
                {
                  path: "application-detail",
                  element: <SCApplication />,
                },
              ],
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
              element: <SwipePage />,
            },
          ],
        },
        {
          path: "signin",
          element: (
            <AuthRouteProtector redirectTo="/dashboard" invertBehavior={true}>
              <SCSignInSide />
            </AuthRouteProtector>
          ),
        },
        {
          path: "signup",
          element: <SignUp />,
          children: [
            {
              path: "",
              element: (
                <AuthRouteProtector
                  redirectTo="/dashboard"
                  invertBehavior={true}
                >
                  <SCSignUp />
                </AuthRouteProtector>
              ),
            },
            {
              path: "user-creation",
              element: <SCUserCreation />,
            },
          ],
        },
      ],
    },
  ],
  {
    future: {
      v7_startTransition: true,
      v7_relativeSplatPath: true,
      v7_fetcherPersist: true,
      v7_normalizeFormMethod: true,
      v7_partialHydration: true,
      v7_skipActionErrorRevalidation: true,
    },
  }
);

export default router;
