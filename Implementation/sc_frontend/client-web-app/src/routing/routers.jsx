import {
  STUDENT_USER_TYPE,
  COMPANY_USER_TYPE,
  UNIVERSITY_USER_TYPE,
} from "../global/globalStatesInit";
import { ConfirmEmail } from "../pages/ConfirmEmail";
import { RouteBase } from "../pages/RouteBase";
import { SCEditCv } from "../components/CV/SCEditCV";
import { SCSignUp } from "../components/SignUp/SCSignUp";
import { createBrowserRouter } from "react-router-dom";
import { SignUp } from "../pages/SignUp";
import { SCUserCreation } from "../components/SignUp/SCUserCreation";
import SCApplicationPreview from "../components/Applications/SCApplicationPreview";
import SCApplication from "../components/Applications/SCApplication";
import React from "react";
import SCSignInSide from "../pages/SCSignInSide";
import About from "../pages/About";
import Contact from "../pages/Contact";
import Main from "../pages/Main";
import Dashboard from "../pages/Dashboard";
import Home from "../pages/Home";
import Settings from "../pages/Settings";
import Feedback from "../pages/Feedback";
import Overview from "../pages/Overview";
import Applications from "../pages/Applications";
import Communications from "../pages/Communications";
import University from "../pages/University";
import Profile from "../pages/Profile";
import Account from "../pages/Account";
import SwipePage from "../pages/SwipePage";
import CV from "../pages/CV";
import Interviews from "../pages/Interviews";
import BrowseInternshipOffers from "../pages/BrowseInternshipOffers";
import InterviewPosOffer from "../pages/InterviewPosOffer";
import Recommendations from "../pages/Recommendations";
import EmailRouteProtector from "./EmailRouteProtector";
import AuthRouteProtector from "./AuthRouteProtector";
import UserCreationRouteProtector from "./UserCreationRouteProtector";
import UserTypeRouteProtector from "./UserTypeRouteProtector";
import SCCommunications from "../components/Communications/SCCommunications";
import SCCommunicationDetails from "../components/Communications/SCCommunicationDetails";
import SCNewCommunication from "../components/Communications/SCNewCommunication";
import VerifyEmail from "../pages/VerifyEmail";
import SCCv from "../components/CV/SCCV";
import SCInterview from "../components/interviews/SCIntOffer";
import SCInterviewsPreview from "../components/Interviews/SCInterviewsPreview";
import { SCInterviewEdit } from "../components/interviews/SCInterviewEdit";
import InternshipOffers from "../pages/InternshipOffers";
import { SCNewIntOffer } from "../components/InternshipOffers/SCNewIntOffer";
import { SCIntOfferEdit } from "../components/InternshipOffers/SCIntOfferEdit";
import SCIntOffer from "../components/InternshipOffers/SCIntOffer";
import SCIntOffersPreview from "../components/InternshipOffers/SCIntOffersPreview";
import SCBrowseInternshipPreview from "../components/BrowseInternshipOffers/SCBrowseInternshipPreview";
import SCBrowseInternshipOffer from "../components/BrowseInternshipOffers/SCBrowseInternshipOffer";
import IntPosOfferPreview from "../components/IntPosOffer/IntPosOfferPreview";
import IntPosOffer from "../components/IntPosOffer/IntPosOffer";
import SCRecommendations from "../components/Recommendations/SCRecommendations";

//NAV Router Configurations

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
              element: (
                <UserTypeRouteProtector allowedTypes={[STUDENT_USER_TYPE]}>
                  <CV />
                </UserTypeRouteProtector>
              ),
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
              element: (
                <UserTypeRouteProtector allowedTypes={[UNIVERSITY_USER_TYPE]}>
                  <University />
                </UserTypeRouteProtector>
              ),
            },
            {
              path: "internship-offers",
              element: (
                <UserTypeRouteProtector allowedTypes={[COMPANY_USER_TYPE]}>
                  <InternshipOffers />
                </UserTypeRouteProtector>
              ),
              children: [
                {
                  path: "",
                  element: <SCIntOffersPreview />,
                },
                {
                  path: "create",
                  element: <SCNewIntOffer />,
                },
                {
                  path: "details/:id",
                  element: <SCIntOffer />,
                },
                {
                  path: "edit/:id",
                  element: <SCIntOfferEdit />,
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
                  path: "details/:id",
                  element: <SCBrowseInternshipOffer />,
                },
              ],
            },
            {
              path: "applications",
              element: (
                <UserTypeRouteProtector
                  allowedTypes={[STUDENT_USER_TYPE, COMPANY_USER_TYPE]}
                >
                  <Applications />
                </UserTypeRouteProtector>
              ),
              children: [
                {
                  path: "",
                  element: <SCApplicationPreview />,
                },
                //{
                //  path: "details/:id",
                //  element: <SCApplication />,
                //},
              ],
            },
            {
              path: "recommendations",
              element: (
                <UserTypeRouteProtector
                  allowedTypes={[STUDENT_USER_TYPE, COMPANY_USER_TYPE]}
                >
                  <Recommendations />
                </UserTypeRouteProtector>
              ),
              children: [
                {
                  path: "",
                  element: <SCRecommendations />,
                },
              ],
            },
            {
              path: "interviews",
              element: (
                <UserTypeRouteProtector
                  allowedTypes={[STUDENT_USER_TYPE, COMPANY_USER_TYPE]}
                >
                  <Interviews />
                </UserTypeRouteProtector>
              ),
              children: [
                {
                  path: "",
                  element: <SCInterviewsPreview />,
                },
                {
                  path: "details/:id",
                  element: <SCInterview />,
                },
                {
                  path: "edit/:id",
                  element: <SCInterviewEdit />,
                },
                {
                  path: "create",
                  element: <SCInterviewEdit />, //FIX put the right page
                },
              ],
            },
            //todo this should be called internship positions offers
            {
              path: "internship-positions-offers",
              element: (
                <UserTypeRouteProtector
                  allowedTypes={[STUDENT_USER_TYPE, COMPANY_USER_TYPE]}
                >
                  <InterviewPosOffer />
                </UserTypeRouteProtector>
              ),
              children: [
                {
                  path: "",
                  element: <IntPosOfferPreview />,
                },
                {
                  path: "details/:id",
                  element: <IntPosOffer />,
                },
              ],
            },
            {
              path: "communications",
              element: <Communications />,
              children: [
                {
                  path: "",
                  element: <SCCommunications />,
                },
                {
                  path: "details/:id",
                  element: <SCCommunicationDetails />,
                },
                {
                  path: "new/:type",
                  element: <SCNewCommunication />,
                },
              ],
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
              element: (
                <UserTypeRouteProtector allowedTypes={[STUDENT_USER_TYPE]}>
                  <SwipePage />
                </UserTypeRouteProtector>
              ),
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
              element: (
                <UserCreationRouteProtector invertBehavior={true}>
                  <SCUserCreation />
                </UserCreationRouteProtector>
              ),
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
