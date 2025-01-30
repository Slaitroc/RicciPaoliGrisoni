import { colorSchemes } from "../components/Templates/shared-theme/themePrimitives";

let logger = false;
let debugMode = false;

export const enableLogger = () => {
  logger = true;
  console.log("Logger enabled");
};

export const enableDebug = () => {
  debugMode = true;
  console.log("Debug enabled");
};

export const disableDebug = () => {
  debugMode = false;
  console.log("Debug disabled");
};

export const disableLogger = () => {
  logger = false;
  console.log("Logger disabled");
};

export const log = (...args) => {
  if (logger) {
    console.log(
      "%cLOG:%c",
      "color: green; font-weight: bold;",
      "color: inherit;",
      ...args
    );
  }
};

export const error = (...args) => {
  if (logger) {
    console.error(
      "%cLOG - ERROR:%c",
      "color: red; font-weight: bold;",
      "color: inherit;",
      ...args
    );
  }
};

export const debug = (...args) => {
  if (debugMode) {
    console.log(
      "%cDEBUG:%c",
      "color: purple; font-weight: bold;",
      "color: inherit;",
      ...args
    );
  }
};

export const focus = (...args) => {
  if (debugMode) {
    console.log(
      "%cFOCUS:%c",
      "color: orange; font-weight: bold;",
      "color: inherit;",
      ...args
    );
  }
};
