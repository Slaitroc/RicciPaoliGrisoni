import { RouterProvider } from "react-router-dom";
import router from "./routing/routers";
import { GlobalProvider } from "./globalContext";

function App() {
  //TODO creare i componenti visivi per testare il codice
  // console.log("rendering app");
  return (
    <GlobalProvider>
      <RouterProvider router={router} />
    </GlobalProvider>
  );
}
export default App;
