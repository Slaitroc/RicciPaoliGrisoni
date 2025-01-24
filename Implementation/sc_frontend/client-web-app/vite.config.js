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
          // Configura Workbox
          workbox: {
            runtimeCaching: [
              {
                urlPattern: /^https:\/\/fonts\.googleapis\.com\//,
                handler: "CacheFirst",
                options: {
                  cacheName: "google-fonts-stylesheets",
                },
              },
              {
                urlPattern: /^https:\/\/fonts\.gstatic\.com\//,
                handler: "CacheFirst",
                options: {
                  cacheName: "google-fonts-webfonts",
                  expiration: {
                    maxEntries: 20,
                    maxAgeSeconds: 60 * 60 * 24 * 365, // 1 anno
                  },
                },
              },
            ],
          },
          // Specifica un Service Worker personalizzato
          srcDir: "src", // Directory dei file sorgente
          filename: "firebase-messaging-sw.js", // Nome del Service Worker personalizzato
          injectRegister: "inline", // Registra automaticamente il SW
        }),
    ].filter(Boolean), // Filtra i plugin nulli per evitare errori
  };
});
