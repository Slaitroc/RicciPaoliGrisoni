const getBaseDomain = () => {
  if (import.meta.env.VITE_USE_STUBS === "true") {
    return "http://localhost:5173";
  } else if (import.meta.env.VITE_USE_STUBS === "false") {
    return "http://localhost"; // Ambiente di staging
  }
};

const getFRontendDomain = () => {
  return "http://localhost:5173";
};

export const BASE_DOMAIN = getBaseDomain();
export const FRONTEND_DOMAIN = getFRontendDomain();
