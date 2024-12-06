import { useEffect } from "react";
import SCNavbar from "./components/SCNavbar/SCNavbar";

function App() {
  useEffect(() => {
    document.body.style.backgroundColor = "#222831";
    document.body.style.color = "#EEEEEE";
  });
  return (
    <div>
      <SCNavbar />
    </div>
  );
}
export default App;
