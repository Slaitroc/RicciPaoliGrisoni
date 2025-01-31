import * as React from "react";
import { styled } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import Breadcrumbs, { breadcrumbsClasses } from "@mui/material/Breadcrumbs";
import NavigateNextRoundedIcon from "@mui/icons-material/NavigateNextRounded";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";

const StyledBreadcrumbs = styled(Breadcrumbs)(({ theme }) => ({
  margin: theme.spacing(1, 0),
  [`& .${breadcrumbsClasses.separator}`]: {
    color: (theme.vars || theme).palette.action.disabled,
    margin: 1,
  },
  [`& .${breadcrumbsClasses.ol}`]: {
    alignItems: "center",
  },
}));

const dashboardBreadcrumbLabels = {
  "": "Overview",
  settings: "Settings",
  cv: "My CV",
  "cv/edit": "Edit CV",
  university: "University",
  "internship-offers": "Internship Offers",
  "internship-offers/internship-detail": "Internship Details",
  "browse-internship-offers": "Browse Internship Offers",
  "browse-internship-offers/internship-detail": "Internship Details",
  applications: "Spontaneous Applications",
  "applications/application-detail": "Application Details",
  recommendations: "Recommendations",
  interviews: "Interviews",
  "interviews/interview-detail": "Interview Details",
  "internship-positions-offers": "Internship Position Offers",
  "internship-positions-offers/details/:id": "Details",
  communications: "Communications",
  profile: "Profile",
  account: "Account Settings",
  "swipe-card": "Swipe Candidates",
};

export default function SCNavbarBreadcrumbs() {
  const location = useLocation();
  const pathnames = location.pathname.split("/").filter((x) => x);
  const basePath =
    pathnames[0] === "dashboard" ? pathnames.slice(1) : pathnames;

  return (
    <StyledBreadcrumbs
      aria-label="breadcrumb"
      separator={<NavigateNextRoundedIcon fontSize="small" />}
    >
      {/* Link alla Dashboard */}
      <Link to="/dashboard">
        <Typography variant="body1" sx={{ color: "text.primary" }}>
          Dashboard
        </Typography>
      </Link>

      {basePath.map((value, index) => {
        const to = `/dashboard/${basePath.slice(0, index + 1).join("/")}`;
        const isLast = index === basePath.length - 1;
        const label =
          dashboardBreadcrumbLabels[basePath.slice(0, index + 1).join("/")] ||
          decodeURIComponent(value);

        return isLast ? (
          <Typography key={to} variant="body1" sx={{ fontWeight: 600 }}>
            {label}
          </Typography>
        ) : (
          <Link key={to} to={to}>
            <Typography variant="body1" sx={{ color: "text.primary" }}>
              {label}
            </Typography>
          </Link>
        );
      })}
    </StyledBreadcrumbs>
  );
}
