import { alpha } from "@mui/material/styles";
import { gray, orange, red } from "../themePrimitives";

/* eslint-disable import/prefer-default-export */
export const feedbackCustomizations = {
  MuiAlert: {
    styleOverrides: {
      root: ({ theme }) => ({
        borderRadius: 10,
        border: `1px solid ${alpha(red[300], 0.5)}`,
        color: (theme.vars || theme).palette.text.primary,

        // Usa il comportamento standard ma aggiungi un controllo personalizzato
        "&.MuiAlert-standardSuccess": {
          backgroundColor: theme.palette.success.light, // Comportamento standard
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
          backgroundColor: theme.palette.info.light, // Ripristina il default di "info"
          border: `1px solid ${alpha(theme.palette.info.main, 0.5)}`,
          "& .MuiAlert-icon": {
            color: theme.palette.info.main,
          },
        },

        // Modalità scura: Applica modifiche dinamiche per le classi di severità
        ...(theme.palette.mode === "dark" && {
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
            backgroundColor: alpha(theme.palette.info.dark, 0.5),
            border: `1px solid ${alpha(theme.palette.info.dark, 0.5)}`,
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
