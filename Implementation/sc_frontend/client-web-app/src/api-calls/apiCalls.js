import { fetchWrapper } from "./fetchWrapper";
import { auth } from "./api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig";

// #region UTILITIES

export const getToken = async () => {
  const user = auth.currentUser;
  if (!user) {
    console.error("Utente non autenticato. Nessun token disponibile.");
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
  console.log("Token: ", token);
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
  console.log(JSON.stringify({ ...userData }));
  console.log("Token: ", token);
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
  return fetchWrapper("/application-api/acc/get-universities", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });
};

export const getInternshipOffers = async () => {
    const token = await getToken();
    return fetchWrapper("/application-api/sub/private/internship/get-internship-offer", {
        method: "GET",
        headers: { Authorization: `Bearer ${token}` },
    });
}

export const getCompanyInternships = async (companyID) => {
  const token = await getToken();
  console.log(JSON.stringify({ companyID }));
  console.log("Token: ", token);
    return fetchWrapper(`/application-api/sub/private/internship/${companyID}/get-company-internships`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
    });
}

export const updateOffer = async (offer) => {
    const token = await getToken();
    return fetchWrapper("/application-api/sub/private/internship/update-my-offer", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ ...offer }),
    });
}

export const getStudentCV = async (studentID) => {
    const token = await getToken();
    console.log(JSON.stringify({ studentID }));
    console.log("Token: ", token);
        return fetchWrapper(`/sub/private/cv/${studentID}/get-student-cv`, {
            method: "GET",
            headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
            },
        });
}

export const updateMyCV = async (cv) => {
    const token = await getToken();
    console.log(JSON.stringify({ ...cv }));
    console.log("Token: ", token);
        return fetchWrapper(`/application-api/sub/private/cv/update-cv`, {
            method: "POST",
            headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
            },
            body: JSON.stringify({ ...cv })
        });
}



export const getMySpontaneousApplications = async () => {
    const token = await getToken();
    return fetchWrapper("/application-api/sub/private/application/get-applications", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
    });
}

export const submitSpontaneousApplication = async (internshipOfferID) => {
    const token = await getToken();
    return fetchWrapper(`/application-api/sub/private/application/${internshipOfferID}/submit`, {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
    });
}

export const acceptSpontaneousApplication = async (applicationID) => {
    const token = await getToken();
    return fetchWrapper(`/application-api/sub/private/application/${applicationID}/accept`, {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
    });

}






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
