import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import { VitePWA } from "vite-plugin-pwa";

export default defineConfig(({ mode }) => {
  const isProduction = mode === "production";

  return {
    base: "./", // Usa percorsi relativi per le risorse
    plugins: [
      react(),
      // Aggiungi il Service Worker della PWA solo in produzione
      isProduction &&
        VitePWA({
          start_url: "./", // Percorso relativo
          scope: "./",
          registerType: "autoUpdate",
          manifest: {
            short_name: "S&C",
            name: "Students & Companies",
            description: "A platform connecting students and companies",
            orientation: "portrait",
            icons: [
              {
                src: "pwa_resources/icon-192x192.png",
                type: "image/png",
                sizes: "192x192",
              },
              {
                src: "pwa_resources/icon-512x512.png",
                type: "image/png",
                sizes: "512x512",
              },
              {
                src: "pwa_resources/icon-256x256.png",
                type: "image/png",
                sizes: "256x256",
              },
            ],
            start_url: "/",
            background_color: "#ffffff",
            display: "standalone",
            scope: "/",
            theme_color: "#000000",
            screenshots: [
              {
                src: "pwa_resources/screenshot-desktop.png",
                type: "image/png",
                sizes: "1280x720",
                form_factor: "wide",
              },
              {
                src: "pwa_resources/screenshot-mobile.png",
                type: "image/png",
                sizes: "750x1334",
                form_factor: "narrow",
              },
            ],
          },
        }),
    ].filter(Boolean), // Filtra i plugin nulli per evitare errori
  };
});
