import * as recommendation from "../../api-calls/api-wrappers/recommendation/recommendation";
import * as offer from "../../api-calls/api-wrappers/submission-wrapper/internshipOffer";
import * as cv from "../../api-calls/api-wrappers/submission-wrapper/cv";
import * as logger from "../../logger/logger";
import React, { createContext, useEffect } from "react";
import { useGlobalContext } from "../../global/GlobalContext";
import Alert from "@mui/material/Alert";
import { useNavigate } from "react-router-dom";

const RecommendationsContext = createContext();

export const useRecommendationsContext = () => {
  const context = React.useContext(RecommendationsContext);
  if (!context) {
    throw new Error(
      "useRecommendationsContext must be used within a RecommendationsProvider"
    );
  }
  return context;
};

export const RecommendationsProvider = ({ children }) => {
  const navigate = useNavigate();
  const handleErrorButtonClick = () => {
    navigate("/dashboard/browse-internship-offers");
  };

  const { profile } = useGlobalContext();
  const [recommendationsData, setRecommendationsData] = React.useState([]);
  const [recommendationOfferList, setRecommendationOfferList] = React.useState(
    []
  );
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertSeverity, setAlertSeverity] = React.useState("success");

  useEffect(() => {
    if (profile.userType === "STUDENT") {
      fetchIfStudent();
    } else if (profile.userType === "COMPANY") {
      fetchIfCompany();
    } else {
      setOpenAlert(true);
      setAlertSeverity("error");
      setAlertMessage("User type not recognized");
    }
  }, []);

  const fetchIfStudent = async () => {
    recommendation.getRecommendations().then((response) => {
      if (!response.success) {
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      } else {
        setRecommendationsData(response.data);
        response.data.forEach((recommendationItem) => {
          logger.debug("recommendation: ", recommendationItem);
          offer
            .getSpecificOffer(recommendationItem.internshipOfferID)
            .then((offerResponse) => {
              if (!offerResponse.success) {
                logger.error(offerResponse.message);
              } else {
                logger.debug("offer: ", offerResponse.data);
                setRecommendationOfferList((prev) => {
                  // Check if the recommendation already exists
                  const exists = prev.some(
                    (item) => item.recommendation.id === recommendationItem.id
                  );
                  if (exists) {
                    return prev;
                  }
                  // Create a new list with the new entry and sort it
                  const newList = [
                    ...prev,
                    {
                      recommendation: recommendationItem,
                      offer: offerResponse.data,
                    },
                  ];
                  newList.sort(
                    (a, b) => a.recommendation.id - b.recommendation.id
                  );
                  return newList;
                });
              }
            });
        });
      }
    });
  };

  const fetchIfCompany = async () => {
    recommendation.getRecommendations().then((response) => {
      if (!response.success) {
        setOpenAlert(true);
        setAlertSeverity(response.severity);
        setAlertMessage(response.message);
      } else {
        //logger.debug("responseData ", response.data);
        setRecommendationsData(response.data);
        response.data.forEach((recommendationItem) => {
          cv.getStudentCV(recommendationItem.studentID).then((cvResponse) => {
            if (!cvResponse.success) {
              logger.error(cvResponse.message);
            } else {
              //logger.debug("cv: ", cvResponse.data);
              setRecommendationOfferList((prev) => {
                // Check if the recommendation already exists
                const exists = prev.some(
                  (item) => item.recommendation.id === recommendationItem.id
                );
                if (exists) {
                  return prev;
                }
                // Create a new list with the new entry and sort it
                const newList = [
                  ...prev,
                  {
                    recommendation: recommendationItem,
                    cv: cvResponse.data,
                  },
                ];
                newList.sort(
                  (a, b) => a.recommendation.id - b.recommendation.id
                );
                //logger.debug("newList: ", newList);
                return newList;
              });
            }
          });
        });
      }
    });
  };

  const value = {
    recommendationsData,
    recommendationOfferList,
    profile,
    handleErrorButtonClick,
  };

  return (
    <RecommendationsContext.Provider value={value}>
      {openAlert && (
        <>
          <Alert severity={alertSeverity}>{alertMessage}</Alert>
        </>
      )}
      {children}
    </RecommendationsContext.Provider>
  );
};
