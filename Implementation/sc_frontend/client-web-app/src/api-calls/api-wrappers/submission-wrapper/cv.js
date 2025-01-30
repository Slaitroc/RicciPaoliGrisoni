import * as apiCalls from "../../apiCalls";

export const getStudentCV = async () => {
  return apiCalls
    .getStudentCV()
    .then((response) => {
      if (response.status === 204) {
        return {
          success: false,
          cv: null,
          message: "No CV found... Let's create one!",
          severity: "info",
        };
      }
      if (response.status === 401) {
        return response.json().then((data) => {
          return {
            success: false,
            cv: null,
            message: data.properties.error,
            severity: "error",
          };
        });
      }
      if (response.status === 404) {
        return response.json().then((data) => {
          return {
            success: false,
            cv: null,
            message: data.properties.error,
            severity: "error",
          };
        });
      }
      if (response.status === 500) {
        return response.json().then((data) => {
          return {
            success: false,
            cv: null,
            message: data.properties.error,
            severity: "error",
          };
        });
      }
      if (response.ok) {
        return response.json().then((data) => {
          return {
            success: true,
            cv: data.properties,
            message: "CV fetched successfully",
            severity: "success",
          };
        });
      }
    })
    .catch((error) => {
      //NOTE lancio errore critico
      throw error;
    });
};

export const updateMyCV = async (cv) => {
  return apiCalls
    .updateMyCV(cv)
    .then((response) => {
      if (response.status === 201) {
        return response.body().then((data) => {
          return {
            success: true,
            message: "CV updated successfully",
            severity: "success",
          };
        });
      }
      if (!response.ok) {
        return response.body().then((data) => {
          return {
            success: false,
            message: data.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      //NOTE lancio errore critico
      throw error;
    });
};
