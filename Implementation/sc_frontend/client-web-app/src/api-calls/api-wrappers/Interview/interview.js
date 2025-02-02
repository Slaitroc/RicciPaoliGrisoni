import * as apiCalls from "../../apiCalls";
import * as logger from "../../../logger/logger";
import * as wrapperUtils from "../wrapperUtils";

export const getInterviews = async () => {
  return apiCalls.getInterviews();
};

/**
 * @deprecated This function is deprecated and will be removed in future releases.
 */
export const getFormattedInterviewsOld = async () => {
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
            severity: response.status === 400 ? "error" : "info",
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

export const getFormattedInterviews = async () => {
  return apiCalls
    .getMyInterviews()
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map();
          fieldMap.set("status", "Status");
          fieldMap.set("hasAnswered", "Answered By Student");
          fieldMap.set("id", "Interview ID");
          fieldMap.set("internshipTitle", "Internship Title");
          fieldMap.set("internshipOfferID", "Internship Offer ID");
          fieldMap.set("companyName", "Company Name");
          fieldMap.set("companyID", "Company ID");
          fieldMap.set("studentName", "Student Name");
          fieldMap.set("studentID", "Student ID");
          fieldMap.set("interviewQuizID", "Student Answers ID");
          fieldMap.set("interviewTemplateID", "Interview Template ID");
          return {
            success: true,
            data: wrapperUtils.formatLabeledArrayContent(
              fieldMap,
              new Map(),
              payload
            ),
            message: "Interviews fetched successfully",
            severity: "success",
          };
        });
      } else {
        return response.json().then((payload) => {
          return {
            success: false,
            data: null,
            message: payload.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const getFormattedInterview = async (interviewID) => {
  return apiCalls
    .getInterview(interviewID)
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map();
          fieldMap.set("status", "Status");
          fieldMap.set("hasAnswered", "Answered By Student?");
          fieldMap.set("id", "Interview ID");
          fieldMap.set("internshipTitle", "Internship Title");
          fieldMap.set("internshipOfferID", "Internship Offer ID");
          fieldMap.set("companyName", "Company Name");
          fieldMap.set("companyID", "Company ID");
          fieldMap.set("studentName", "Student Name");
          fieldMap.set("studentID", "Student ID");
          fieldMap.set("interviewQuizID", "Student Answers ID");
          fieldMap.set("interviewTemplateID", "Student Answers ID");
          return {
            success: true,
            data: wrapperUtils.formatContent(
              fieldMap,
              new Map(),
              payload.properties
            ),
            message: "Interview fetched successfully",
            severity: "success",
          };
        });
      } else {
        return response.json().then((payload) => {
          return {
            success: false,
            data: null,
            message: payload.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const getFormattedStudentAnswers = async (quizID) => {
  return apiCalls
    .getStudentAnswers(quizID)
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map();
          fieldMap.set("id", "Student Answers ID"); //quizID
          fieldMap.set("evaluation", "Evaluation");
          fieldMap.set("answer1", "Answer 1");
          fieldMap.set("answer2", "Answer 2");
          fieldMap.set("answer3", "Answer 3");
          fieldMap.set("answer4", "Answer 4");
          fieldMap.set("answer5", "Answer 5");
          fieldMap.set("answer6", "Answer 6");
          return {
            success: true,
            data: wrapperUtils.formatContent(
              fieldMap,
              new Map(),
              payload.properties
            ),
            message: "Student answers fetched successfully",
            severity: "success",
          };
        });
      } else {
        return response.json().then((payload) => {
          return {
            success: false,
            data: null,
            message: payload.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const getFormattedInterviewTemplateQuestions = async (interviewID) => {
  return apiCalls
    .getMyTemplateInterview(interviewID)
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          const fieldMap = new Map();
          const typeMap = new Map();
          fieldMap.set("id", "Interview Template ID");
          fieldMap.set("question1", "Question 1");
          fieldMap.set("question2", "Question 2");
          fieldMap.set("question3", "Question 3");
          fieldMap.set("question4", "Question 4");
          fieldMap.set("question5", "Question 5");
          fieldMap.set("question6", "Question 6");
          return {
            success: true,
            data: wrapperUtils.formatContent(
              fieldMap,
              new Map(),
              payload.properties
            ),
            message: "Interview questions fetched successfully",
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
              message: payload.properties.error,
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

export const sendInterviewQuestions = async (interviewID, questions) => {
  const payload = {};

  questions.reduce((acc, value, index) => {
    acc[`question${index + 1}`] = value;
    return acc;
  }, payload);
  logger.debug("Question values", payload);
  return apiCalls
    .sendInterview(interviewID, payload)
    .then((response) => {
      if (response.status === 200) {
        return response.json().then((payload) => {
          return {
            success: true,
            data: payload.properties,
            message: payload.properties.message,
            severity: "success",
          };
        });
      } else {
        return response.json().then((payload) => {
          return {
            success: false,
            data: null,
            message: payload.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};

export const sendInterviewAnswers = async (interviewID, answers) => {
  const payload = {};
  Object.entries(answers).reduce((acc, [key, value], index) => {
    acc[`answer${index + 1}`] = value;
    return acc;
  }, payload);
  return apiCalls
    .sendInterviewAnswer(interviewID, payload)
    .then((response) => {
      if (response.ok) {
        return response.json().then((payload) => {
          logger.focus(payload);
          return {
            success: true,
            data: payload.properties,
            message: "Answers sent successfully",
            severity: "success",
          };
        });
      } else {
        return response.json().then((payload) => {
          return {
            success: false,
            data: null,
            message: payload.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      throw error;
    });
};
