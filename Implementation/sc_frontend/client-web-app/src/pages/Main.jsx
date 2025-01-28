import React, { useEffect } from "react";
import SCBlog from "../components/Main/SCBlog";
import { useGlobalContext } from "../global/GlobalContext";
import { useNavigate } from "react-router-dom";

const Main = () => {
  const { setNavigator, navigator } = useGlobalContext();
  const navigate = useNavigate();

  useEffect(() => {
    setNavigator(navigate);
  }, [navigate]);

  return <SCBlog></SCBlog>;
};

export default Main;
