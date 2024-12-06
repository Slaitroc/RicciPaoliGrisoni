const CACHE_NAME = "pwa-cache-v1";
const API_CACHE = "api-cache-v1";
const urlsToCache = [
  "/", // Home page
  "/index.html", // File HTML principale
  "/offline.html", // Pagina di fallback offline
  "/pwa_resources/icon-192x192.png", // Icone PWA
  "/pwa_resources/icon-512x512.png",
];

// Installazione del Service Worker
self.addEventListener("install", (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      console.log("Aperto il cache:", CACHE_NAME);
      return cache.addAll(urlsToCache);
    })
  );
});

// Attivazione e pulizia delle vecchie cache
self.addEventListener("activate", (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((cache) => {
          if (cache !== CACHE_NAME && cache !== API_CACHE) {
            console.log("Cache vecchia rimossa:", cache);
            return caches.delete(cache);
          }
        })
      );
    })
  );
});

// Intercettazione delle richieste di rete
self.addEventListener("fetch", (event) => {
  const url = new URL(event.request.url);

  // Gestione delle chiamate API (Network First)
  if (url.pathname.startsWith("/api/")) {
    event.respondWith(
      fetch(event.request)
        .then((response) => {
          const responseClone = response.clone();
          caches.open(API_CACHE).then((cache) => {
            cache.put(event.request, responseClone);
          });
          return response;
        })
        .catch(() => {
          console.error("API non disponibile:", event.request.url);
          return caches.match(event.request);
        })
    );
    return;
  }

  // Gestione delle richieste statiche (Cache First)
  event.respondWith(
    caches.match(event.request).then((response) => {
      return (
        response ||
        fetch(event.request).catch(() => {
          // Fallback per pagine HTML
          if (event.request.destination === "document") {
            return caches.match("/offline.html");
          }
        })
      );
    })
  );
});
