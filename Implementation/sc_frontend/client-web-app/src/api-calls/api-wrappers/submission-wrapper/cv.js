import * as apiCalls from "../../apiCalls";

export const getStudentCV = async () => {
  return new Promise(async (resolve, reject) => {
    try {
      apiCalls.getStudentCV();
    } catch (err) {
      console.error(err);
      reject(err);
    }
  });
};
