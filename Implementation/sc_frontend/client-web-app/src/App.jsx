import { RouterProvider } from "react-router-dom";
import router from "./routing/routers";
import { GlobalProvider } from "./global/GlobalContext";
import SCNotificationAlert from "./components/Shared/SCNotificationAlert";
import * as logger from "./logger/logger";

logger.enableLogger();
logger.enableDebug();

function App() {
  // TODO provare a mettere un check sull'autenticazione quidel tipo, invio il token al server e se non è valido cambio lo stato di isAuthenticated
  return (
    <GlobalProvider>
      <SCNotificationAlert />
      <RouterProvider router={router} />
    </GlobalProvider>
  );
}
export default App;
