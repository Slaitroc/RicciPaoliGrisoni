import { useMotionValue, useTransform, motion } from 'framer-motion';
import CardTemplate from './CardTemplate';
import './Swiper.css';

const LIMIT = 200;

function SwipeableCard({ card, index, items, setItems }) {
  const x = useMotionValue(0);
  const opacity = useTransform(x, [-LIMIT, 0, LIMIT], [0, 1, 0]);
  const rotate = useTransform(x, [-LIMIT, -40, 0, 40, LIMIT], [-18, 0, 0, 0, 18]);

  function handleDragEnd(event, info) {
    if (info.offset.x > LIMIT) {
      console.log("Swiped right");
    } else if (info.offset.x < -LIMIT) {
      console.log("Swiped left");
    } else {
      return;
    }
    setItems(prevItems => prevItems.filter((_, i) => i !== index));
  }

  return (
    <motion.div
      key={index}
      className="swipeableCard"
      style={{
        zIndex: items.length - index,
        x,
        opacity,
        rotate
      }}
      drag="x"
      dragConstraints={{ left: 0, right: 0 }}
      onDragEnd={handleDragEnd}
    >
      <CardTemplate
        companyName={card.companyName}
        position={card.position}
        description={card.description}
      />
    </motion.div>
  );
}

function SwipeCardManager({ items, setItems }) {
  return (
    <div className="mainContainer">
      <div className="cardContainer">
        {items.map((card, index) => (
          <SwipeableCard
            key={index}
            card={card}
            index={index}
            items={items}
            setItems={setItems}
          />
        ))}
      </div>
    </div>
  );
}

export default SwipeCardManager;