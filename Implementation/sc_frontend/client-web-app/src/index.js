import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import * as serviceWorkerRegistration from "./serviceWorkerRegistration"; // Importa il file per registrare il Service Worker
import "@fontsource/roboto/300.css";
import "@fontsource/roboto/400.css";
import "@fontsource/roboto/500.css";
import "@fontsource/roboto/700.css";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// Registra il Service Worker
serviceWorkerRegistration.register({
  onUpdate: (registration) => {
    if (
      window.confirm("Un nuovo aggiornamento è disponibile. Vuoi ricaricare?")
    ) {
      if (registration && registration.waiting) {
        registration.waiting.postMessage({ type: "SKIP_WAITING" });
        registration.waiting.addEventListener("statechange", (event) => {
          if (event.target.state === "activated") {
            window.location.reload();
          }
        });
      }
    }
  },
  onSuccess: () => {
    console.log("Service Worker registrato con successo.");
  },
});

// Misura le performance della tua app
reportWebVitals();
