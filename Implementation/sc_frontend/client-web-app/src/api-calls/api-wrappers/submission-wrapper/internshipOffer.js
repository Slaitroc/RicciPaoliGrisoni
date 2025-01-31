import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";
import * as wrapperUtils from "../wrapperUtils";

export const getInternshipOffers = async () => {
  return apiCalls.getInternshipOffers();
};

export const getCompanyInternships = async (companyID) => {
  return apiCalls.getCompanyInternships(companyID);
};

export const getFormattedCompanyInternships = async (companyID) => {
  return apiCalls
    .getCompanyInternships(companyID)
    .then((response) => {
      if (response.status === 204) {
        return {
          success: false,
          data: null,
          message: "No internship offers found for this company",
          severity: "info",
        };
      } else if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map();
          fieldMap.set("id", "Internship ID");
          fieldMap.set("companyID", "Company ID");
          fieldMap.set("companyName", "Company Name");
          fieldMap.set("title", "Title");
          fieldMap.set("startDate", "Start Date");
          fieldMap.set("endDate", "End Date");
          fieldMap.set("duration", "Duration");
          fieldMap.set("numberPositions", "Number of Positions");
          fieldMap.set("location", "Location");
          fieldMap.set("description", "Description");
          fieldMap.set("requiredSkills", "Required Skills");
          fieldMap.set("compensation", "Compensation");

          logger.debug(
            wrapperUtils.formatLabeledArrayContent(fieldMap, payload)
          );
          return {
            success: true,
            data: wrapperUtils.formatLabeledArrayContent(fieldMap, payload),
            message: "Internship offers fetched successfully",
            severity: "success",
          };
        });
      } else {
        return {
          success: false,
          data: null,
          message: response.properties.error,
          severity: "error",
        };
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const getFormattedInternships = async () => {
  try {
    return apiCalls.getInternshipOffers().then((response) => {
      logger.focus("getFormattedInternships status: ", response);
      if (response.status === 204) {
        return {
          success: false,
          data: null,
          message: "No internship offers found",
          severity: "info",
        };
      } else {
        return response.json().then((payload) => {
          return {
            success: true,
            data: payload.map((offer) => offer.properties),
            message: "Internship offers fetched successfully",
            severity: "success",
          };
        });
      }
    });
  } catch (error) {
    throw error;
  }
};

export const getSpecificOffer = async (offerID) => {
  try {
    return apiCalls.getSpecificOffer(offerID).then((response) => {
      if (response.status === 404) {
        return {
          success: false,
          data: null,
          message: response.properties.error,
          severity: "error",
        };
      } else {
        return response.json().then((payload) => {
          const { properties } = payload;
          return {
            success: true,
            data: {
              id: properties.id,
              title: properties.title,
              companyID: properties.companyID,
              companyName: properties.companyName,
              description: properties.description,
              startDate: properties.startDate,
              endDate: properties.endDate,
              duration: properties.duration,
              location: properties.location,
              compensation: properties.compensation,
              numberPositions: properties.numberPositions,
              requiredSkills: properties.requiredSkills,
            },
            message: "Internship offer fetched successfully",
            severity: "success",
          };
        });
      }
    });
  } catch (error) {
    throw error;
  }
};
