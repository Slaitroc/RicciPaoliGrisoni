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
  settings: "Settings",
  cv: "My CV",
  "cv/edit": "Edit CV",
  university: "University",
  "internship-offers": "Internship Offers",
  "internship-offers/create": "Create Internship Offer",
  "internship-offers/details/:id": "Internship Details",
  "internship-offers/edit/:id": "Edit Internship Offer",
  "browse-internship-offers": "Browse Internship Offers",
  "browse-internship-offers/details/:id": "Internship Details",
  applications: "Spontaneous Applications",
  "applications/application-detail/:id": "Application Details",
  recommendations: "Recommendations",
  interviews: "Interviews",
  "interviews/details/:id": "Interview Details",
  "interviews/check/:id": "Check Interview",
  "interviews/create": "Create Interview",
  "interviews/answer/:id": "Answer Interview",
  "internship-positions-offers": "Internship Position Offers",
  "internship-positions-offers/details/:id": "Internship Position Details",
  "internship-offers-template": "Internship Offer Templates",
  communications: "Communications",
  "communications/details/:id": "Communication Details",
  "communications/new/:type": "New Communication",
  profile: "Profile",
  account: "Account Settings",
  "swipe-card": "Swipe Candidates",
};

export default function SCNavbarBreadcrumbs() {
  const location = useLocation();
  const pathnames = location.pathname.split("/").filter((x) => x);
  const basePath = pathnames[0] === "dashboard" ? pathnames.slice(1) : pathnames;

  // 🔥 Funzione per trovare la label corretta (gestisce i parametri dinamici)
  const getBreadcrumbLabel = (path) => {
    if (dashboardBreadcrumbLabels[path]) return dashboardBreadcrumbLabels[path];

    const matchingKey = Object.keys(dashboardBreadcrumbLabels).find((key) => {
      const staticPath = key.replace(/\/:[a-zA-Z]+/g, ""); // Rimuove i parametri dinamici
      return path.startsWith(staticPath);
    });

    return dashboardBreadcrumbLabels[matchingKey] || decodeURIComponent(path);
  };

  return (
    <StyledBreadcrumbs
      aria-label="breadcrumb"
      separator={<NavigateNextRoundedIcon fontSize="small" />}
    >
      {/* Link alla Dashboard */}
      {basePath.length > 0 && (
        <Link to="/dashboard">
          <Typography variant="body1" sx={{ color: "text.primary" }}>
            Dashboard
          </Typography>
        </Link>
      )}

      {basePath.map((value, index) => {
        const to = `/dashboard/${basePath.slice(0, index + 1).join("/")}`;
        const isLast = index === basePath.length - 1;
        const label = getBreadcrumbLabel(basePath.slice(0, index + 1).join("/"));

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
