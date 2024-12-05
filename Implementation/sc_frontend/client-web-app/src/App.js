import logo from "./logo.svg";

import { useEffect } from "react";
import ScButton from "./components/ButtonUsage";
import Navbar from "./components/Navbar/Navbar";

if ("serviceWorker" in navigator) {
  window.addEventListener("load", () => {
    navigator.serviceWorker
      .register("/service-worker.js")
      .then((registration) => {
        console.log(
          "Service Worker registrato con successo:",
          registration.scope
        );
      })
      .catch((error) => {
        console.error(
          "Errore durante la registrazione del Service Worker:",
          error
        );
      });
  });
}else{
  console.error("serviceWorker not supported in this browser. Try to update and run the app again")  
}

function App() {
  useEffect(() => {
    document.body.style.backgroundColor = "#222831";
    document.body.style.color = "#EEEEEE";
  });
  return (
    <div>
      <Navbar />
    </div>
  );
}
export default App;
