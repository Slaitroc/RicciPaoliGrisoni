import { motion, useMotionValue, useTransform, animate } from "framer-motion";
import React from "react";
import RecommendationCard from "./RecommendationCard";
import { useGlobalContext } from "../../../global/GlobalContext";
import * as apiCall from "../../../api-calls/apiCalls";
import * as logger from "../../../logger/logger";
import FeedbackOverlay from "../FeedbackOverlay";
const AnimatedCard = ({
  item,
  index,
  isHidden,
  removeCard,
  SWIPELIMIT = 150,
  ROTATELIMIT = 20,
  ZOOMLIMIT = 1.2,
  BORDERCOLOR = "white",
  ACCEPTBORDERCOLOR = "green",
  REJECTBORDERCOLOR = "red",
}) => {
  const x = useMotionValue(0);
  const { profile } = useGlobalContext();
  const [feedbackNeeded, setFeedbackNeeded] = React.useState(false);
  const rotate = useTransform(
    x,
    [-SWIPELIMIT, -10, 0, 10, SWIPELIMIT],
    [-ROTATELIMIT, 0, 0, 0, ROTATELIMIT]
  );
  const scale = useTransform(
    x,
    [-SWIPELIMIT, 0, SWIPELIMIT],
    [ZOOMLIMIT, 1, ZOOMLIMIT]
  );

  const borderColor = useTransform(
    x,
    [-SWIPELIMIT, -SWIPELIMIT + 1, 0, SWIPELIMIT - 1, SWIPELIMIT],
    [
      REJECTBORDERCOLOR,
      REJECTBORDERCOLOR,
      BORDERCOLOR,
      BORDERCOLOR,
      ACCEPTBORDERCOLOR,
    ]
  );

  //We need to declare all hooks before the return statement, else a hook order error will be thrown.
  if (isHidden) return null;

  const handleDragEnd = () => {
    logger.debug("Recommendation: ", item.recommendation);
    const currentX = x.get();
    if (currentX > SWIPELIMIT) {
      console.log("Swiped right");
      apiCall.acceptRecommendation(item.recommendation.id);
      if (checkFeedbackNeeded()) {
        setFeedbackNeeded(true);
      } else {
        animateOut(x);
      }
    } else if (currentX < -SWIPELIMIT) {
      console.log("Swiped left");
      apiCall.rejectRecommendation(item.recommendation.id);
      animateOut(x);
    } else {
      animateBack(x);
    }
  };

  const animateOut = (x) => {
    animate(x, x.get() * 5, { duration: 0.2, ease: "easeOut" }).then(() => {
      removeCard(index);
    });
  };

  const animateBack = (x) => {
    console.log("Not swiped enough");
    animate(x, 0, { duration: 0.2, ease: "easeOut" });
    x.set(0);
  };

  const completeAnimation = () => {
    animateOut(x);
  };

  const checkFeedbackNeeded = () => {
    return (
      (profile.userType === "COMPANY" &&
        item.recommendation.status === "acceptedByStudent") ||
      (profile.userType === "STUDENT" &&
        item.recommendation.status === "acceptedByCompany")
    );
  };

  return (
    (feedbackNeeded && (
      <FeedbackOverlay
        setFeedbackNeeded={setFeedbackNeeded}
        onComplete={completeAnimation}
        recommendation={item.recommendation}
      />
    )) || (
      <motion.div
        style={{
          position: "absolute",
          x,
          rotate,
          scale,
          border: `6px solid`,
          borderColor,
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          borderRadius: 10,
        }}
        drag="x"
        dragConstraints={{ left: -SWIPELIMIT * 2, right: +SWIPELIMIT * 2 }}
        dragElastic={0}
        onDragEnd={handleDragEnd}
        //onDrag={() => {
        //  logger.debug(x.get());
        //}}
      >
        <RecommendationCard
          recommendation={item.recommendation}
          //its cv.cv probably a hack but it works, we got not time right now to fix it
          otherPair={profile.userType === "STUDENT" ? item.offer : item.cv.cv}
          borderColor={borderColor}
          sx={{ width: "100%", height: "100%" }}
        />
      </motion.div>
    )
  );
};

export default AnimatedCard;
