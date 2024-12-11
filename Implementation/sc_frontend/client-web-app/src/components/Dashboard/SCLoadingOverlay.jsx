import * as React from "react";
import Box from "@mui/material/Box";
import { DataGrid } from "@mui/x-data-grid";
import { useDemoData } from "@mui/x-data-grid-generator";
import { CircularProgress } from "@mui/material";

const SCLoadingOverlay = () => {
  return (
    <Box sx={{ width: "100%", height: 400 }}>
      <CircularProgress />
    </Box>
  );
};
