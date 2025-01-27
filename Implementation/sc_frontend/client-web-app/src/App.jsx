import { RouterProvider } from "react-router-dom";
import router from "./routing/routers";
import { GlobalProvider } from "./global/GlobalContext";

function App() {
  // TODO provare a mettere un check sull'autenticazione quidel tipo, invio il token al server e se non è valido cambio lo stato di isAuthenticated
  return (
    <GlobalProvider>
      <RouterProvider router={router} />
    </GlobalProvider>
  );
}
export default App;
