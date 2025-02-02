import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";

export const getInterviews = async () => {
  return apiCalls.getInterviews();
};

export const getFormattedInterviewPosOffers = async () => {
  try {
    return apiCalls.getMyInternshipPositionOffers().then(async (response) => {
      logger.debug("getFormattedInterviewPosOffers response: ", response);
      if (response.status === 200) {
        focus("here3");
        return (payload = response.json().then((payload) => {
          return {
            success: true,
            data: payload.map((item) => item.properties),
            message: "Internship Position Offer fetched successfully",
            severity: "success",
          };
        }));
      } else if (response.status === 204) {
        focus("here2");
        return {
          success: true,
          data: null,
          message: "No Internship Position Offer found", // NOTAAA response.properties.error non esisteee
          severity: "info",
        };
      } else {
        focus("here1");
        return {
          success: false,
          data: null,
          message: response.properties.error,
          severity: "error",
        };
      }
    });
  } catch (error) {
    logger.error(error);
  }
};

export const acceptInternshipPositionOffer = async (id) => {
  try {
    return apiCalls.acceptInternshipPositionOffer(id).then((response) => {
      logger.focus("here");
      if (response.status === 400 || response.status === 404) {
        // Add return here to propagate the error object
        return response.json().then((data) => {
          return {
            success: false,
            message: data.properties.error,
            severity: "error",
          };
        });
      } else {
        return response.json().then((data) => {
          return {
            success: true,
            message: "Internship Position Offer accepted successfully",
            severity: "success",
          };
        });
      }
    });
  } catch (error) {
    logger.error(error);
  }
};

export const rejectInternshipPositionOffer = async (id) => {
  try {
    return apiCalls.rejectInternshipPositionOffer(id);
  } catch (error) {
    logger.error(error);
  }
};
