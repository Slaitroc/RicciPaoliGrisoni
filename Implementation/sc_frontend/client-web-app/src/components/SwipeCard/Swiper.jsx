import React from 'react';
import './Swiper.css';
import CardTemplate from './CardTemplate';
import {motion} from 'framer-motion';

function SwipeCard({ items }) {
    return (
        <div className="mainContainer">
            <div className="cardContainer">
                {items.map((card, index) => (
                    <motion.div key={index} className="swipeableCard"
                        style={{
                            zIndex: items.length - index,
                        }}
                        drag="x"
                        dragConstraints={{ left: 0, right: 0 }}
                        >
                        <CardTemplate
                            companyName={card.companyName}
                            position={card.position}
                            description={card.description}
                        />
                    </motion.div>
                ))}
            </div>
        </div>
    );
}

export default SwipeCard;