import { auth } from "./firebaseConfig";

export const getToken = () => {
  auth.currentUser.getIdToken().then((token) => {
    return token;
  });
};
