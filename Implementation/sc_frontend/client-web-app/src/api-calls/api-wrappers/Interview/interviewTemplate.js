import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";
import * as wrapperUtils from "../wrapperUtils";

export const getFormattedInterviewTemplates = async () => {
  return apiCalls
    .getMyTemplateInterviews()
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map([
            ["id", "Template ID"],
            ["question1", "Question 1"],
            ["question2", "Question 2"],
            ["question3", "Question 3"],
            ["question4", "Question 4"],
            ["question5", "Question 5"],
            ["question6", "Question 6"],
          ]);
          return {
            success: true,
            data: wrapperUtils.formatLabeledArrayContent(
              fieldMap,
              new Map(),
              payload
            ),
            message: "Interview templates fetched successfully",
            severity: "success",
          };
        });
      } else {
        return response
          .json()
          .then((payload) => {
            return {
              success: false,
              data: null,
              message: response?.properties.error
                ? response.properties.error
                : "Unknown error occurred",
              severity: "error",
            };
          })
          .catch((error) => {
            throw error;
          });
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const getNoInterviewMatch = async () => {
  return apiCalls
    .getMatchNoInterview()
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map();
          fieldMap.set("id", "Interview ID");
          fieldMap.set("internshipTitle", "Internship Title");
          fieldMap.set("internshipOfferID", "Internship Offer ID");
          fieldMap.set("companyName", "Company Name");
          fieldMap.set("companyID", "Company ID");
          fieldMap.set("studentName", "Student Name");
          fieldMap.set("studentID", "Student ID");
          fieldMap.set("status", "Status");
          fieldMap.set("interviewQuizID", "Student Answers ID");
          fieldMap.set("interviewTemplateID", "Interview Template ID");
          fieldMap.set("hasAnswered", "Has Student Answers?");
          return {
            success: true,
            data: wrapperUtils.formatLabeledArrayContent(
              fieldMap,
              new Map(),
              payload
            ),
            message: "Internship offers fetched successfully",
            severity: "success",
          };
        });
      } else {
        return response.json().then((payload) => {
          const errorMessage =
            Array.isArray(payload) && payload[0]?.properties?.error
              ? payload[0].properties.error
              : "Unknown error occurred";

          return {
            success: false,
            data: null,
            message: errorMessage,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      return {
        success: false,
        data: null,
        message: error.message || "Request failed",
        severity: "error",
      };
    });
};
