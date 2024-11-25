import logo from "./logo.svg";

import { useEffect } from "react";
import ScButton from "./components/ButtonUsage";
import Navbar from "./components/Navbar/Navbar";

function App() {
  useEffect(() => {
    document.body.style.backgroundColor = "#222831";
    document.body.style.color = "#EEEEEE";
  });
  return (
    <div>
      <Navbar/> 
    </div>
  );
}
export default App;
