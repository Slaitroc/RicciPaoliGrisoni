// Importa il plugin Terser per la minificazione
import { terser } from "@rollup/plugin-terser";

export default {
  input: "src/index.js", // Specifica il file di ingresso principale
  output: {
    file: "dist/bundle.js", // File di output generato
    format: "iife", // Formato adatto per l'esecuzione nei browser
    sourcemap: true, // Abilita la generazione della mappa del codice sorgente
  },
  plugins: [
    terser(), // Usa il plugin Terser per minificare il codice
  ],
};
