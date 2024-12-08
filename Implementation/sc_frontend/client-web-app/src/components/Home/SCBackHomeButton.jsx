import * as React from "react";
import PropTypes from "prop-types";
import Badge, { badgeClasses } from "@mui/material/Badge";
import IconButton from "@mui/material/IconButton";
import { useNavigate } from "react-router-dom"; // Importa l'hook useNavigate
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

function SCBackHomeButton({ showBadge = false, ...props }) {
  const navigate = useNavigate(); // Inizializza il navigatore

  const handleClick = () => {
    navigate("/"); // Naviga alla home ("/" è il percorso della home)
  };

  return (
    <Badge
      color="error"
      variant="dot"
      invisible={!showBadge}
      sx={{ [`& .${badgeClasses.badge}`]: { right: 2, top: 2 } }}
    >
      <IconButton size="small" {...props} onClick={handleClick}>
        <ArrowBackIcon />
      </IconButton>
    </Badge>
  );
}

SCBackHomeButton.propTypes = {
  showBadge: PropTypes.bool,
};

export default SCBackHomeButton;
