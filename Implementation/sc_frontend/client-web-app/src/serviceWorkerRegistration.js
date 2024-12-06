// Registra il Service Worker
export function register(config) {
  if ("serviceWorker" in navigator) {
    // Attendi che la pagina si carichi prima di registrare il Service Worker
    window.addEventListener("load", () => {
      navigator.serviceWorker
        .register("/service-worker.js") // Nome corretto del file Service Worker
        .then((registration) => {
          console.log("Service Worker registrato:", registration);

          // Gestisci il successo della registrazione
          if (config && config.onSuccess) {
            config.onSuccess(registration);
          }

          // Gestisci gli aggiornamenti del Service Worker
          if (config && config.onUpdate) {
            registration.onupdatefound = () => {
              const installingWorker = registration.installing;
              if (installingWorker) {
                installingWorker.onstatechange = () => {
                  if (
                    installingWorker.state === "installed" &&
                    navigator.serviceWorker.controller
                  ) {
                    // Notifica l'utente di un aggiornamento
                    config.onUpdate(registration);
                  }
                };
              }
            };
          }
        })
        .catch((error) => {
          console.error(
            "Errore durante la registrazione del Service Worker:",
            error
          );
        });
    });
  } else {
    console.warn("Il browser non supporta i Service Worker.");
  }
}

// Deregistra il Service Worker
export function unregister() {
  if ("serviceWorker" in navigator) {
    navigator.serviceWorker.ready
      .then((registration) => {
        registration.unregister();
        console.log("Service Worker deregistrato.");
      })
      .catch((error) => {
        console.error(
          "Errore durante la deregistrazione del Service Worker:",
          error
        );
      });
  }
}
