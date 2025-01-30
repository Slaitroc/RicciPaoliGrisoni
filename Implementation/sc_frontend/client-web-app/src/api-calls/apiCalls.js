import { fetchWrapper } from "./fetchWrapper";
import { auth } from "./api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";
import * as logger from "../logger/logger";

// #region UTILITIES

export const getToken = async () => {
  const user = auth.currentUser;
  if (!user) {
    return ""; // Token vuoto se l'utente non è autenticato
  }
  return await user.getIdToken();
};

// #endregion UTILITIES

//-----------------------------------------------------------------------

// #region NOTIFICATION API CALLS
// These are the calls to notification-api endpoints

// #region GLOBAL NOTIFICATION API CALLS
// These are the apis that are not called directly from the components

export const sendNotificationToken = async (notificationToken) => {
  const token = await getToken();
  logger.debug("Token: ", token);
  return fetchWrapper("/notification-api/private/send-token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      // DANGER - può essere nullo il token?? significa che l'utente non è loggato
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ notificationToken }),
  });
};

// #endregion GLOBAL NOTIFICATION API CALLS

// #endregion NOTIFICATION API CALLS

//-----------------------------------------------------------------------

// #region AUTHORIZATION API CALLS
// These are the calls concerning the authorization of the user
// These calls reach the application-api endpoints that require some interaction with the auth-api endpoints

// #region GLOBAL AUTHORIZATION API CALLS
// These are the apis that are not called directly from the components

// FIX This call is not used in production
// it is used only for testing purposes as it should be catched by Mock Service Worker
// TODO To handle this case and switch between the firebase implementation and this one we can use a flag in the .env file.... I leave this method as a reminder to do so in the future
export const login = async (email, password) => {
  return fetchWrapper("/auth-api/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
};

export const sendEmailConfirmed = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/acc/private/confirm-user", {
    method: "POST",
    headers: { Authorization: `Bearer ${token}` },
  });
};

export const sendUserData = async (userData) => {
  const token = await getToken();
  //FIX remove logs
  logger.debug(JSON.stringify({ ...userData }));
  logger.debug("Token: ", token);
  return fetchWrapper("/application-api/acc/private/send-user-data", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ ...userData }),
  });
};

// #endregion GLOBAL AUTHORIZATION API CALLS

// #endregion AUTHORIZATION API CALLS

//-----------------------------------------------------------------------

// #region APPLICATION API CALLS
// These are the calls that reach the application-api endpoints

export const getUniversities = async () => {
  //   throw new Error("Not implemented");
  return fetchWrapper("/application-api/acc/get-universities", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });
};

export const getInternshipOffers = async () => {
  const token = await getToken();
  return fetchWrapper(
    "/application-api/sub/private/internship/get-internship-offer",
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const getCompanyInternships = async (companyID) => {
  const token = await getToken();
  logger.debug(JSON.stringify({ companyID }));
  logger.debug("Token: ", token);
  return fetchWrapper(
    `/application-api/sub/private/internship/${companyID}/get-company-internships`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const updateOffer = async (offer) => {
  const token = await getToken();
  return fetchWrapper(
    "/application-api/sub/private/internship/update-my-offer",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ ...offer }),
    }
  );
};

export const getStudentCV = async (studentID) => {
  const token = await getToken();
  logger.debug(JSON.stringify({ studentID }));
  logger.debug("Token: ", token);
  return fetchWrapper(
    `/application-api/sub/private/cv/${studentID}/get-student-cv`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const updateMyCV = async (cv) => {
  const token = await getToken();
  logger.debug(JSON.stringify({ ...cv }));
  logger.debug("Token: ", token);
  return fetchWrapper(`/application-api/sub/private/cv/update-cv`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ ...cv }),
  });
};

export const getMySpontaneousApplications = async () => {
  const token = await getToken();
  return fetchWrapper(
    "/application-api/sub/private/application/get-my-applications",
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const submitSpontaneousApplication = async (internshipOfferID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/sub/private/application/${internshipOfferID}/submit`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const acceptSpontaneousApplication = async (applicationID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/sub/private/application/${applicationID}/accept`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const rejectSpontaneousApplication = async (applicationID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/sub/private/application/${applicationID}/reject`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const getMyRecommendations = async () => {
  const token = await getToken();
  return fetchWrapper(
    "/application-api/recommendation/private/get-my-matches",
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const acceptRecommendation = async (recommendationID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/recommendation/private/${recommendationID}/accept`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const rejectRecommendation = async (recommendationID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/recommendation/private/${recommendationID}/reject`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const getMyCommunications = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/comm/private/get-my-comm", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
};

export const getCommunication = async (communicationID) => {
  const token = await getToken();
  return fetchWrapper(
    `/comm/private/communication/${communicationID}/get-messages`,
    {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const createCommunication = async (communication) => {
  const token = await getToken();
  return fetchWrapper("/application-api/comm/private/create-comm", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ ...communication }),
  });
};

export const terminateCommunication = async (communicationID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/comm/private/${communicationID}/terminate`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const submitFeedback = async (feedback) => {
  const token = await getToken();
  return fetchWrapper("/application-api/comm/private/submit-feedback", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ ...feedback }),
  });
};

export const getMyInterviews = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/interview/private/get-my-interviews", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
};

export const getMyTemplateInterviews = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/interview/private/get-my-templates", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
};

export const sendInterview = async (interviewID, questions) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${interviewID}/send-interview`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ ...questions }),
    }
  );
};

export const sendInterviewAnswer = async (InterviewID, answer) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${InterviewID}/send-answer`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ ...answer }),
    }
  );
};

//todo perché gli serve una interview per salvare il template?
export const saveInterviewTemplate = async (template) => {
  const token = await getToken();
  return fetchWrapper(
    "/application-api/interview/private/${InterviewID}/save-template",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ ...template }),
    }
  );
};

export const sendInterviewTemplate = async (
  templateInterviewID,
  interviewID
) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${templateInterviewID}/send-template/${interviewID}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const sendInterviewEvaluation = async (interviewID, evaluation) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${interviewID}/evaluate-interview`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ ...evaluation }),
    }
  );
};

export const sendInternshipPositionOffer = async (interviewID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${interviewID}/send-int-pos-off`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const getMyInternshipPositionOffers = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/interview/private/get-my-int-pos-off", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const acceptInternshipPositionOffer = async (intPosOffID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${intPosOffID}/accept-int-pos-off`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

export const rejectInternshipPositionOffer = async (intPosOffID) => {
  const token = await getToken();
  return fetchWrapper(
    `/application-api/interview/private/${intPosOffID}/reject-int-pos-off`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );
};

// #region GLOBAL APPLICATION API CALLS
// These are the apis that are not called directly from the components

export const getUserData = async () => {
  const token = await getToken();
  return fetchWrapper("/application-api/acc/private/get-user-data", {
    method: "GET",
    headers: { Authorization: `Bearer ${token}` },
  });
};

// #endregion GLOBAL APPLICATION API CALLS

// #endregion APPLICATION API CALLS

//-----------------------------------------------------------------------

// #region TESTING API CALLS
// These are the calls used for testing purposes in specific testing pages that won't be available in production

export const testRequest = async () => {
  return fetchWrapper("/application-api/sub/private/internship/8", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer 11111",
    },
  });
};

export const testHello = async () => {
  return fetchWrapper("/application-api/hello", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer 11111",
    },
  });
};

export const testProxy = async () => {
  return fetchWrapper("/application-api/private/test", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer 11111",
    },
  });
};

// #endregion TESTING API CALLS
