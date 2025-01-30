import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";
export const getInterviews = async () => {
  return apiCalls.getInterviews();
};

export const getFormattedInterviews = async () => {
  try {
    return apiCalls.getMyInterviews().then((response) => {
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
            severity: "error",
          };
        });
      } else {
        return response.json().then((payload) => {
          const formattedData = payload.map((interview) => {
            const { properties } = interview;
            return {
              id: properties.id,
              status: properties.status,
              internshipTitle: properties.internshipTitle,
              companyName: properties.companyName,
              companyID: properties.companyID,
              studentName: properties.studentName,
              studentID: properties.studentID,
            };
          });
          return {
            status: true,
            data: formattedData,
            message: "Interviews fetched successfully",
            severity: "success",
          };
        });
      }
    });
  } catch (error) {
    logger.error(error);
  }
};
