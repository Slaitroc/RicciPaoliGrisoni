import { motion, useMotionValue, useTransform, animate } from "framer-motion";
import RecommendationCard from "./RecommendationCard";
import * as apiCall from "../../api-calls/apiCalls";
import * as logger from "../../logger/logger";
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
    [-SWIPELIMIT, -SWIPELIMIT / 2, 0, SWIPELIMIT / 2, SWIPELIMIT],
    [
      REJECTBORDERCOLOR,
      REJECTBORDERCOLOR,
      BORDERCOLOR,
      BORDERCOLOR,
      ACCEPTBORDERCOLOR,
    ]
  );

  if (isHidden) return null;

  const handleDragEnd = () => {
    const currentX = x.get();
    if (currentX > SWIPELIMIT) {
      console.log("Swiped right");
      apiCall.acceptRecommendation(item.recommendation.id);
    } else if (currentX < -SWIPELIMIT) {
      console.log("Swiped left");
      apiCall.rejectRecommendation(item.recommendation.id);
    } else {
      return;
    }
    animate(x, x.get() * 5, { duration: 0.2, ease: "easeOut" }).then(() => {
      removeCard(index);
    });
  };

  return (
    <motion.div
      style={{
        position: "absolute",
        x,
        rotate,
        scale,
        border: `4px solid`,
        borderColor,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
      drag="x"
      dragConstraints={{ left: 0, right: 0 }}
      onDragEnd={handleDragEnd}
      onDrag={() => {
        logger.debug(x.get());
      }}
    >
      <RecommendationCard
        recommendation={item.recommendation}
        offer={item.offer}
        borderColor={borderColor}
        sx={{ width: "100%", height: "100%" }}
      />
    </motion.div>
  );
};

export default AnimatedCard;
