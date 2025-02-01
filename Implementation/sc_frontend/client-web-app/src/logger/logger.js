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
    console.groupCollapsed(
      "%cLOG:%c",
      "color: green; font-weight: bold;",
      "color: inherit;",
      ...args
    );
    console.trace();
    console.groupEnd();
  }
};

export const error = (...args) => {
  if (logger) {
    console.groupCollapsed(
      "%cLOG - ERROR:%c",
      "color: red; font-weight: bold;",
      "color: inherit;",
      ...args
    );
    console.trace();
    console.groupEnd();
  }
};

export const debug = (...args) => {
  if (debugMode) {
    console.groupCollapsed(
      "%cDEBUG:%c",
      "color: purple; font-weight: bold;",
      "color: inherit;",
      ...args
    );
    console.trace();
    console.groupEnd();
  }
};

export const focus = (...args) => {
  console.groupCollapsed(
    "%cFOCUS:%c",
    "color: orange; font-weight: bold;",
    "color: inherit;",
    ...args
  );
  console.trace();
  console.groupEnd();
};
