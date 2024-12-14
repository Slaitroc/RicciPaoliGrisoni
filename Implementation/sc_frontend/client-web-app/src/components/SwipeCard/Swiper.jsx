import { useMotionValue, useTransform, motion, color } from 'framer-motion';
import CardTemplate from './CardTemplate';
import {useState} from 'react';
import './Swiper.css';

const LIMIT = 80;

function SwipeableCard({ content, index, items, setItems }) {
    const x = useMotionValue(0);
    //const opacity = useTransform(x, [-LIMIT, 0, LIMIT], [0, 1, 0]);
    const rotate = useTransform(x, [-LIMIT, -10, 0, 10, LIMIT], [-10, 0, 0, 0, 10]);
    const borderColor = useTransform(x, [-LIMIT, -LIMIT + 1, 0, LIMIT - 1, LIMIT], ["#ff0000", "#000000", "#000000", "#000000", "#00ff00"]);
    const scale = useTransform(x, [-LIMIT, 0, LIMIT], [1.1, 1, 1.1]);

    function handleDragEnd(event, info) {
        console.log("Info: ", info);
        console.log("Event: ", event);
        if (x.get() > LIMIT) {
            console.log("Swiped right");
        } else if (x.get() < -LIMIT) {
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
                //opacity,
                rotate,
                borderColor,
                scale
            }}
            drag={true}
            dragConstraints={{ left: 0, right: 0, top: 0, bottom: 0 }}
            onDragEnd={handleDragEnd}
        >
            <CardTemplate
                companyName={content.companyName}
                position={content.position}
                description={content.description}
                />
        </motion.div>
    );
}

function SwipeCardManager({ data }) {
    const [items, setItems] = useState(data);
    return (
        <div className="mainContainer">
            <div className="cardContainer">
                {items.map((item, index) => (
                    <SwipeableCard
                        key={index}
                        content={item}
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