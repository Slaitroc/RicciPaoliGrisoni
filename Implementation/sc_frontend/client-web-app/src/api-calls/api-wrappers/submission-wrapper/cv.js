import * as logger from "../../../logger/logger";
import * as apiCalls from "../../apiCalls";

export const getStudentCV = async (userID) => {
  return apiCalls
    .getStudentCV(userID)
    .then((response) => {
      if (response.status === 204) {
        return {
          success: true,
          data: { cv: null },
          message: "No CV found... Let's create one!",
          severity: "info",
        };
      }
      if (response.status === 401) {
        return response.json().then((data) => {
          return {
            success: false,
            data: { cv: null },
            message: data.properties.error,
            severity: "error",
          };
        });
      }
      if (response.status === 404) {
        return response.json().then((data) => {
          return {
            success: false,
            data: { cv: null },
            message: data.properties.error,
            severity: "error",
          };
        });
      }
      if (response.status === 500) {
        return response.json().then((data) => {
          return {
            success: false,
            data: { cv: null },
            message: data.properties.error,
            severity: "error",
          };
        });
      }
      if (response.ok) {
        return response.json().then((data) => {
          const fieldMap = new Map();
          fieldMap.set("studentID", "Student ID");
          fieldMap.set("id", "Curriculum ID");
          fieldMap.set("updateTime", "Last Update");
          fieldMap.set("studentName", "Student Name");
          fieldMap.set("contacts", "Contacts");
          fieldMap.set("spokenLanguages", "Spoken Language");
          fieldMap.set("education", "Education");
          fieldMap.set("certifications", "Certifications");
          fieldMap.set("workExperiences", "Work Experience");
          fieldMap.set("project", "Projects");
          fieldMap.set("skills", "Skills");

          const orderedCV = {};
          fieldMap.forEach((label, key) => {
            orderedCV[key] = {
              serverField: key, // Nome del campo nel server
              label, // Label desiderata
              value: data.properties[key], // Valore ricevuto dal server
            };
          });
          logger.focus("Received OrderedCV", orderedCV);

          return {
            success: true,
            data: { cv: orderedCV },
            message: "CV fetched successfully",
            severity: "success",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const updateMyCV = async (cvData) => {
  const formattedCvData = {};
  Object.entries(cvData).forEach(([key, value]) => {
    if (key === "studentID" || key === "id" || key === "studentName") {
      return;
    }
    formattedCvData[key] = value.value;
  });

  logger.focus("Sent CV", formattedCvData);

  return apiCalls
    .updateMyCV(formattedCvData)
    .then((response) => {
      if (response.status === 201) {
        return {
          success: true,
          message: "CV updated successfully",
          severity: "success",
        };
      }
      if (!response.ok) {
        return response.body().then((data) => {
          return {
            success: false,
            message: data.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};
