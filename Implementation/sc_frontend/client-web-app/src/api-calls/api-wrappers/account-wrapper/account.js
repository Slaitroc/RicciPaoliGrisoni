import * as apiCalls from "../../apiCalls";

export const sendUserData = async (userData) => {
  return apiCalls.sendUserData(userData);
};

export const getUserData = async () => {
  return apiCalls.getUserData();
};

export const getUniversities = async () => {
  try {
    const response = await apiCalls.getUniversities();
    if (response.status === 204) {
      const universities = ["No universities found"];
      return { names: universities, table: null };
    } else if (response.ok) {
      const body = await response.json();
      const universities = [];
      Object.keys(body.properties).forEach((key) => {
        universities.push(key);
      });
      return { names: universities, table: body.properties };
    } else {
      const universities = "Fetch error";
      return { names: universities, table: null };
    }
  } catch (error) {
    console.error("Error during getUniversities:", error.message);
    throw error;
  }
};
