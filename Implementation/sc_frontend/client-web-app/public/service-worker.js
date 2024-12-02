const CACHE_NAME = "pwa-cache-v1";
const urlsToCache = [
  "/", // Home page
  "/index.html", // File HTML principale
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
          if (cache !== CACHE_NAME) {
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
  event.respondWith(
    caches.match(event.request).then((response) => {
      return response || fetch(event.request);
    })
  );
});

self.addEventListener("fetch", (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      return (
        response ||
        fetch(event.request).catch(() => {
          console.error("Resource not available:", event.request.url);
        })
      );
    })
  );
});
