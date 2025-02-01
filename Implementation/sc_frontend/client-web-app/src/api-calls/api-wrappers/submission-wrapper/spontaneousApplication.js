import * as apiCalls from "../../apiCalls";

export const getMySpontaneousApplications = async () => {
  return new Promise(async (resolve, reject) => {
    try {
      apiCalls.getMySpontaneousApplications();
    } catch (err) {
      console.error(err);
      reject(err);
    }
  });
};

export const getFormattedSpontaneousApplications = async () => {
  try {
    return apiCalls.getMySpontaneousApplications().then((response) => {
      console.log(response);
      if (response.status === 204) {
        return {
          success: false,
          data: null,
          message: "No spontaneous applications found for this user",
          severity: "info",
        };
      } else if (response.status === 404 || response.status === 400) {
        return {
          success: false,
          data: null,
          message: response.properties.error,
          severity: "error",
        };
      } else {
        return response.json().then((payload) => {
          return {
            success: true,
            data: payload.map((item) => item.properties),
            message: "Spontaneous applications fetched successfully",
            severity: "success",
          };
        });
      }
    });
  } catch {
    throw error;
  }
};
