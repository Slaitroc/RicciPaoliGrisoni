import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";
export const getInterviews = async () => {
  return apiCalls.getInterviews();
};

export const getFormattedInterviews = async () => {
  try {
    return apiCalls.getMyInterviews().then((response) => {
      if (response.status === 400) {
        logger.error("User is not a student or a company");
        return {
          status: false,
          data: null,
          message: "User is not a student or a company",
          severity: "error",
        };
      } else if (response.status === 404) {
        logger.debug("No interviews found");
        return {
          status: false,
          data: null,
          message: "No interviews found",
          severity: "info",
        };
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
