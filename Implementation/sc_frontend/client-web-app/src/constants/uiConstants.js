import * as globalStateInit from "../global/globalStatesInit";

export const TEXT = {
  LOGO_NAME: "S&C",
  FULL_SIGN: "Students & Companies",
  USER_NAME:
    globalStateInit.INIT_USER_TYPE == globalStateInit.company
      ? "Company Name"
      : globalStateInit.INIT_USER_TYPE == globalStateInit.student
      ? "Student Name"
      : "University Name",
  NO_NAME: "No user name",
  NO_EMAIL: "No email",
};
