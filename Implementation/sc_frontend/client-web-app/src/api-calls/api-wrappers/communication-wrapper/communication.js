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
      } else if (response.status === 401) {
        return response.json().then((data) => {
          return {
            success: false,
            data: null,
            message: data.properties.error,
            severity: "error",
          };
        });
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
          return {
            success: true,
            data: payload.map((item) => item.properties),
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

export const getFormattedMessages = async (communicationID) => {
  try {
    return apiCalls
      .getCommunicationMessages(communicationID)
      .then((response) => {
        logger.debug("getFormattedMessages status: ", response.status);

        if (response.status === 204) {
          return {
            success: true,
            data: null,
            message: "No messages in communication",
            severity: "info",
          };
        } else if (!response.ok) {
          return response.body().then((data) => {
            return {
              success: false,
              message: data.properties.error,
              severity: "error",
            };
          });
        } else {
          return response.json().then((payload) => {
            logger.log("Payload fetched successfully", payload);
            return {
              success: true,
              data: payload.map((item) => item.properties),
              message: "Messages fetched successfully",
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

export const createCommunication = async (communicationData) => {
  logger.debug("Communication submitted: ", communicationData);

  return apiCalls
    .createCommunication(communicationData)
    .then((response) => {
      if (response.status === 201) {
        return {
          success: true,
          message: "Communication created successfully",
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

export const sendMessage = async (communicationID, message) => {
  logger.debug(
    "Message submitted: ",
    message,
    "to communication: ",
    communicationID
  );

  return apiCalls
    .sendMessage(communicationID, message)
    .then((response) => {
      if (response.status === 201) {
        return {
          success: true,
          message: "Message sent successfully",
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
