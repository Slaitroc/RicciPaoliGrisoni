import { worker } from "./browser";

async function enableMocking() {
  if (import.meta.env.VITE_USE_STUBS === "true") {
    console.log("Starting MSW for mocking API calls...");

    await worker.start({
      onUnhandledRequest: (req) => {
        if (
          req.url.origin === self.location.origin && // Richiesta locale
          (req.url.pathname.endsWith(".png") ||
            req.url.pathname.endsWith(".jpg") ||
            req.url.pathname.endsWith(".css") ||
            req.url.pathname.endsWith(".js") ||
            req.url.pathname.endsWith(".ico") ||
            req.url.pathname.startsWith("/assets/"))
        ) {
          // Ignora risorse statiche
          return;
        }

        console.warn(
          `[MSW] Warning: Intercepted an unhandled ${req.method} request to ${req.url.href}`
        );
      },
    });
  } else {
    console.log("MSW is disabled. Using real API calls.");
  }
}

enableMocking();
