import { QueryClient } from "@tanstack/react-query";
import { persistQueryClient } from "@tanstack/react-query-persist-client";
import { createSyncStoragePersister } from "@tanstack/query-sync-storage-persister";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 5, // Dati freschi per 5 minuti
      cacheTime: 1000 * 60 * 30, // Mantieni i dati in cache per 30 minuti
      refetchOnWindowFocus: false, // Disabilita il refetch quando la finestra torna in focus
    },
  },
});

const persister = createSyncStoragePersister({
  storage: window.localStorage,
});

persistQueryClient({
  queryClient,
  persister,
  maxAge: 1000 * 60 * 60 * 24, // Persisti per 24 ore
});

export default queryClient;
