import {useState} from "react";
import SwipeCardManager from "../components/SwipeCard/Swiper";
import { use } from "react";

const SwipePage = () => {
  const matchCards = [
    { show: true, companyName: "Google", position: "Software Intern", description: "Google is a multinational technology company that specializes in Internet-related services and products.Google is a multinational technology company that specializes in Internet-related services and productsGoogle is a multinational technology company that specializes in Internet-related services and productsGoogle is a multinational technology company that specializes in Internet-related services and products" },
    { show: true, companyName: "Microsoft", position: "Software Intern", description: "Microsoft is an American multinational technology company that produces computer software, consumer electronics, personal computers, and related services." },
    { show: true, companyName: "Zoom", position: "Software Intern", description: "Zoom Video Communications, Inc. is an American communications technology company headquartered in San Jose, California." }
  ];

  const[items, setItems] = useState(matchCards);
  return <SwipeCardManager 
  items={items}
  setItems={setItems}>
  </SwipeCardManager>;
};

export default SwipePage;
