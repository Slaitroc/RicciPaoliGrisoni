import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";
import * as wrapperUtils from "../wrapperUtils";

export const getInternshipOffers = async () => {
  return apiCalls.getInternshipOffers();
};

export const getCompanyInternships = async (companyID) => {
  return apiCalls.getCompanyInternships(companyID);
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
          const fieldTypeMap = new Map();
          fieldMap.set("id", "Internship ID");
          fieldMap.set("companyID", "Company ID");
          fieldMap.set("companyName", "Company Name");
          fieldMap.set("title", "Title");
          fieldTypeMap.set("title", "string");
          fieldMap.set("startDate", "Start Date");
          fieldTypeMap.set("startDate", "date");
          fieldMap.set("endDate", "End Date");
          fieldTypeMap.set("endDate", "date");
          fieldMap.set("duration", "Duration");
          fieldTypeMap.set("duration", "int");
          fieldMap.set("numberPositions", "Number of Positions");
          fieldTypeMap.set("numberPositions", "int");
          fieldMap.set("location", "Location");
          fieldMap.set("description", "Description");
          fieldMap.set("requiredSkills", "Required Skills");
          fieldMap.set("compensation", "Compensation");
          fieldTypeMap.set("compensation", "int");
          fieldMap.set("updateTime", "Last Update");
          fieldTypeMap.set("updateTime", "date-time");
          return {
            success: true,
            data: wrapperUtils.formatLabeledArrayContent(
              fieldMap,
              fieldTypeMap,
              payload
            ),
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

//TODO add the function to get the formatted internships
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

export const sendUpdateMyOffer = async (offerData) => {
  const payload = {};
  for (const key in offerData) {
    if (offerData[key].value) {
      payload[key] = offerData[key].value;
    }
  }
  logger.focus("PRESEND DATA ", payload);
  return apiCalls.updateOffer(payload).then((response) => {
    if (response.status === 201) {
      return response.json().then((payload) => {
        return {
          success: true,
          data: payload.properties,
          message: "Internship offers fetched successfully",
          severity: "success",
        };
      });
    } else {
      return response.json().then((data) => {
        return {
          success: false,
          message: data.properties.error,
          severity: "error",
        };
      });
    }
  });
};
