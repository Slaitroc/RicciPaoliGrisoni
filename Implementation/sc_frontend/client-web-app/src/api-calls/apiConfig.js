const getBaseDomain = () => {
  if (import.meta.env.VITE_USE_STUBS === "true") {
    return "http://localhost:5173";
  } else if (import.meta.env.VITE_USE_STUBS === "false") {
    return "http://10.147.19.176"; // Ambiente di staging
  }
};

export const BASE_DOMAIN = getBaseDomain();
