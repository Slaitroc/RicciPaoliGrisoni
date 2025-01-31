import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";

export const getInterviews = async () => {
  return apiCalls.getInterviews();
};

export const getFormattedInterviewPosOffers = async () => {
  try {
    return apiCalls.getMyInternshipPositionOffers().then((response) => {
      logger.debug("getFormattedInterviewPosOffers response: ", response);
      if (response.status === 400 || response.status === 404) {
        return response.json().then((errorData) => {
          // Extract error message from the parsed array structure
          const errorMessage =
            errorData[0]?.properties?.error || "Unknown error occurred";
          logger.error(errorMessage);
          return {
            success: false,
            data: null,
            message: errorMessage,
            severity: response.status === 400 ? "error" : "info",
          };
        });
      } else {
        return response.json().then((payload) => {
          return {
            success: true,
            data: payload.map((item) => item.properties),
            message: "Internship Position Offer fetched successfully",
            severity: "success",
          };
        });
      }
    });
  } catch (error) {
    logger.error(error);
  }
};
