import * as apiCall from "../../apiCalls";
import * as logger from "../../../logger/logger";

export const getRecommendations = async () => {
  return apiCall.getMyRecommendations().then((response) => {
    try {
      if (response.status === 204) {
        return {
          success: false,
          data: null,
          message:
            "No eligible recommendations found for this user, try again later",
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
          const formattedData = payload.map((recommendation) => {
            const { properties } = recommendation;
            return {
              id: properties.id,
              status: properties.status,
              studentName: properties.studentName,
              studentID: properties.studentID,
              companyName: properties.companyName,
              companyID: properties.companyID,
              internshipOfferTitle: properties.internshipOfferTitle,
              internshipOfferID: properties.internshipOfferID,
              score: properties.score,
            };
          });
          return {
            success: true,
            data: formattedData,
            message: "Recommendations fetched successfully",
            severity: "success",
          };
        });
      }
    } catch (err) {
      logger.error(err);
      throw err;
    }
  });
};
