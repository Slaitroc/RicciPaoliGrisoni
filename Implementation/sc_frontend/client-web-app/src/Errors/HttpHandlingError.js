export class HttpHandlingError extends Error {
  constructor(message, status, statusText) {
    super(message);
    this.name = "HttpHandlingError"; // Nome specifico per questo errore
    this.status = status || 500; // Valore predefinito per lo status
    this.statusText = statusText || "Internal Server Error"; // Testo predefinito

    // Aggiunge lo stack trace se disponibile
    if (Error.captureStackTrace) {
      Error.captureStackTrace(this, HttpHandlingError);
    }
  }

  toString() {
    return `HttpResponse Needs an Handler:
Status Code: ${this.status}
Status Message: ${this.statusText}
Error Info: ${this.message}`;
  }
}
