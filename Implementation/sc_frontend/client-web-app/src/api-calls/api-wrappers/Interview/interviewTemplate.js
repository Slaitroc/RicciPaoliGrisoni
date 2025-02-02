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
            data: wrapperUtils.formatContent(fieldMap, new Map(), payload),
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
