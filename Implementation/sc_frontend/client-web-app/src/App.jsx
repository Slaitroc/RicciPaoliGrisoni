import { RouterProvider } from "react-router-dom";
import router from "./routing/routers";
import { GlobalProvider } from "./global/GlobalContext";
import SCNotificationAlert from "./components/Shared/SCNotificationAlert";
import * as logger from "./logger/logger";

logger.enableLogger();
// logger.enableDebug();

function App() {
  return (
    <GlobalProvider>
      <SCNotificationAlert />
      <RouterProvider router={router} />
    </GlobalProvider>
  );
}
export default App;
