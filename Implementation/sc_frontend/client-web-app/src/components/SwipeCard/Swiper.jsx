import { useMotionValue, useTransform, motion, animate } from 'framer-motion';
import CardTemplate from './CardTemplate';
import { useState } from 'react';
import './Swiper.css';

const SWIPELIMIT = 80; 
const ZOOMLIMIT = 1.1; 
const ROTATELIMIT = 10;
const BORDERCOLOR = "#ffffff";
const ACCEPTBORDERCOLOR = "#00ff00";
const REJECTBORDERCOLOR = "#ff0000";



function SwipeableCard({ content, index, items, setItems }) {
    const x = useMotionValue(0);
    const y = useMotionValue(0);

    const rotate = useTransform(x, [-SWIPELIMIT, -10, 0, 10, SWIPELIMIT], [-ROTATELIMIT, 0, 0, 0, ROTATELIMIT]);
    //until the card x position is (in abs value) smaller than swipe limit, the border color is white, then it changes to green or red
    const borderColor = useTransform(x, [-SWIPELIMIT, -SWIPELIMIT + 1, 0, SWIPELIMIT - 1, SWIPELIMIT], [REJECTBORDERCOLOR, BORDERCOLOR, BORDERCOLOR, BORDERCOLOR, ACCEPTBORDERCOLOR]);
    const scale = useTransform(x, [-SWIPELIMIT, 0, SWIPELIMIT], [ZOOMLIMIT, 1, ZOOMLIMIT]);

    //handle the end of the drag event (will call the back and and remove the swipped card)
    function handleDragEnd() {
        if (x.get() > SWIPELIMIT) {
            console.log("Swiped right");
        } else if (x.get() < -SWIPELIMIT) {
            console.log("Swiped left");
        } else {
            return;
        }
        //animate the card to the right or left and then call the removeCard function
        animate(x, x.get() * 5, { duration: 0.2, ease: "easeOut" }).then(() => removeCard());
        
    }

    //remove the card from the array, reset the x position
    function removeCard() {
        setItems(prevItems => prevItems.filter((_, i) => i !== index));
        x.set(0);
    }

    //return a motion div that contains the CardTemplate component
    return (
        <motion.div
            key={index}
            className="swipeableCard"
            style={{
                zIndex: index,
                x,
                //opacity,
                rotate,
                borderColor,
                scale
            }}
            drag="x"
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

/*
Contains the main container for the swipeable cards and the div that contains the cards.
Create an array of items from the data prop and set the state of the items with the array.
For each item in the item array, a SwipeableCard component is created with a index, the content (description ecc) and the function to edit the card array
*/
function SwipeCardManager({ data }) {
    const [items, setItems] = useState(data);
    return (
        <div className="mainContainer">
            <div className="cardContainer">
                {items.length === 0 ? <button onClick={() => setItems(data)}>Reset</button> : null}
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