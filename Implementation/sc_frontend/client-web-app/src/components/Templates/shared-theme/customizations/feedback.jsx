import { alpha } from "@mui/material/styles";
import { gray, orange, red } from "../themePrimitives";

/* eslint-disable import/prefer-default-export */
export const feedbackCustomizations = {
  MuiAlert: {
    styleOverrides: {
      root: ({ theme }) => ({
        borderRadius: 10,
        color: (theme.vars || theme).palette.text.primary,
        border: `1px solid ${alpha(red[300], 0.5)}`,

        // Classi generate automaticamente da Material UI
        "&.MuiAlert-standardSuccess": {
          backgroundColor: theme.palette.success.light,
          border: `1px solid ${alpha(theme.palette.success.main, 0.5)}`,
          "& .MuiAlert-icon": {
            color: theme.palette.success.main,
          },
        },
        "&.MuiAlert-standardError": {
          backgroundColor: theme.palette.error.light,
          border: `1px solid ${alpha(theme.palette.error.main, 0.5)}`,
          "& .MuiAlert-icon": {
            color: theme.palette.error.main,
          },
        },
        "&.MuiAlert-standardWarning": {
          backgroundColor: theme.palette.warning.light,
          border: `1px solid ${alpha(theme.palette.warning.main, 0.5)}`,
          "& .MuiAlert-icon": {
            color: theme.palette.warning.main,
          },
        },
        "&.MuiAlert-standardInfo": {
          backgroundColor: orange[100],
          border: `1px solid ${alpha(orange[300], 0.5)}`,
          "& .MuiAlert-icon": {
            color: orange[500],
          },
        },

        // Modalità scura
        ...theme.applyStyles("dark", {
          "&.MuiAlert-standardSuccess": {
            backgroundColor: alpha(theme.palette.success.dark, 0.5),
            border: `1px solid ${alpha(theme.palette.success.dark, 0.5)}`,
          },
          "&.MuiAlert-standardError": {
            backgroundColor: alpha(theme.palette.error.dark, 0.5),
            border: `1px solid ${alpha(theme.palette.error.dark, 0.5)}`,
          },
          "&.MuiAlert-standardWarning": {
            backgroundColor: alpha(theme.palette.warning.dark, 0.5),
            border: `1px solid ${alpha(theme.palette.warning.dark, 0.5)}`,
          },
          "&.MuiAlert-standardInfo": {
            backgroundColor: alpha(orange[900], 0.5),
            border: `1px solid ${alpha(orange[800], 0.5)}`,
          },
        }),
      }),
    },
  },
  MuiDialog: {
    styleOverrides: {
      root: ({ theme }) => ({
        "& .MuiDialog-paper": {
          borderRadius: "10px",
          border: "1px solid",
          borderColor: (theme.vars || theme).palette.divider,
        },
      }),
    },
  },
  MuiLinearProgress: {
    styleOverrides: {
      root: ({ theme }) => ({
        height: 8,
        borderRadius: 8,
        backgroundColor: gray[200],
        ...theme.applyStyles("dark", {
          backgroundColor: gray[800],
        }),
      }),
    },
  },
};
