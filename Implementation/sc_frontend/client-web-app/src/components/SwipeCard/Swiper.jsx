import React from 'react';
import './Swiper.css';
import CardTemplate from './CardTemplate';
import { motion, useMotionValue, useTransform } from 'framer-motion';

function SwipeCardHolder({ items }) {
    return (
        <div className="mainContainer">
            <div className="cardContainer">
                {items.map((card, index) => {
                    const x = useMotionValue(0);
                    const opacity = useTransform(x, [-200, 0, 200], [0, 1, 0]);
                    const rotate = useTransform(x, [-200, -40, 0, 40, 200], [-18, 0, 0, 0, 18]);
                    return (<motion.div key={index} className="swipeableCard"
                        style={{
                            zIndex: items.length - index,
                            x,
                            opacity,
                            rotate
                        }}
                        drag="x"
                        dragConstraints={{ left: 0, right: 0 }}
                    >
                        <CardTemplate
                            companyName={card.companyName}
                            position={card.position}
                            description={card.description}
                        />
                    </motion.div>)

                }
                )}
            </div>
        </div>
    );
}

export default SwipeCardHolder;