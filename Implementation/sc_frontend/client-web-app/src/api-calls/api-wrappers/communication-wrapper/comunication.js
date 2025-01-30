import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";

export const getFormattedCommunications = async () => {
  try {
    return apiCalls.getMyCommunications().then((response) => {
      logger.debug("getFormattedCommunications status: ", response.status);

      if (response.status === 204) {
        return {
          success: false,
          data: null,
          message: "No communications found",
          severity: "info",
        };
      } else if (response.status === 404) {
        return response.json().then((data) => {
          return {
            success: false,
            data: null,
            message: data.properties.error,
            severity: "error",
          };
        });
      } else if (response.status === 500) {
        return response.json().then((data) => {
          return {
            success: false,
            data: null,
            message: data.properties.error,
            severity: "error",
          };
        });
      } else {
        return response.json().then((payload) => {
          const formattedData = payload.map((communication) => {
            const { properties } = communication;
            return {
              id: properties.id,
              type: properties.type,
              title: properties.title,
              content: properties.content,
              internshipOfferID: properties.internshipOfferID,
              internshipOfferTitle: properties.internshipOfferTitle,
              companyName: properties.companyName,
              studentName: properties.studentName,
              universityName: properties.universityName,
            };
          });
          return {
            success: true,
            data: formattedData,
            message: "Communications fetched successfully",
            severity: "success",
          };
        });
      }
    });
  } catch (error) {
    // NOTE: Lancio errore critico
    throw error;
  }
};
