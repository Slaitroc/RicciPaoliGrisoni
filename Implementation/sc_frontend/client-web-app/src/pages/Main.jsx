import React, { useEffect } from "react";
import SCBlog from "../components/Main/SCBlog";
import { useGlobalContext } from "../global/GlobalContext";
import { useNavigate } from "react-router-dom";
import * as account from "../api-calls/api-wrappers/account-wrapper/account.js";
import { onAuthStateChanged } from "firebase/auth";
import * as firebaseConfig from "../api-calls/api-wrappers/authorization-wrapper/firebase-utils/firebaseConfig.js";

const Main = () => {
  return <SCBlog></SCBlog>;
};

export default Main;
