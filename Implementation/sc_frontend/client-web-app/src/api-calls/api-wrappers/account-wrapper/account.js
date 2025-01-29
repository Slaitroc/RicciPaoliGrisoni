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
      const data = await response.json();
      console.error("Error during getUniversities:", data.properties.error);
      const universities = ["Fetch error"];
      return { names: universities, table: null };
    }
  } catch (error) {
    //NOTE lancio errore critico
    throw error;
  }
};

export const getUniversitiesv2 = async () => {
  return apiCalls
    .getUniversities()
    .then((response) => {
      if (response.status === 204) {
        const universities = ["No universities found"];
        return { success: true, names: universities, table: null };
      } else if (response.ok) {
        return response.json().then((body) => {
          const universities = [];
          Object.keys(body.properties).forEach((key) => {
            universities.push(key);
          });
          return { success: true, names: universities, table: body.properties };
        });
      } else {
        response.json().then((data) => {
          console.error("Error during getUniversities:", data.properties.error);
          const universities = ["Fetch error"];
          return {
            success: false,
            names: universities,
            table: null,
            message: data.properties.error,
            severity: "error",
          };
        });
      }
    })
    .catch((error) => {
      //NOTE lancio errore critico
      console.error(
        "Error critico durante il fetch di getUniversities():",
        error.message
      );
      throw error;
    });
};
