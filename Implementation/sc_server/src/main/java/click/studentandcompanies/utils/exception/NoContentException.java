package click.studentandcompanies.utils.exception;

public class NoContentException extends RuntimeException {
    // Exception for when there is no content in the database to return
    // It return to the sender the 204 status code

    /**
     * @param message Message to be communicated to the client
     * @throws NoContentException there is no content in the database to return. It return to the sender the 204 status code
     */
    public NoContentException(String message) {
        super(message);
    }
}
