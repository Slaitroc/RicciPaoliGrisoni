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

export const acceptInternshipPositionOffer = async (id) => {
  try {
    return apiCalls.acceptInternshipPositionOffer(id).then((response) => {
      if (response.status === 400 || response.status === 404) {
        logger.debug("error occurred", response);
        // Add return here to propagate the error object
        return response.json().then((data) => {
          return {
            success: false,
            message: data.properties.error,
            severity: "error",
          };
        });
      } else {
        // Success case returns directly
        return {
          success: true,
          message: "Internship Position Offer accepted successfully",
          severity: "success",
        };
      }
    });
  } catch (error) {
    logger.error(error);
  }
};

export const rejectInternshipPositionOffer = async (id) => {
  try {
    return apiCalls.rejectInternshipPositionOffer(item.id);
  } catch (error) {
    logger.error(error);
  }
};
