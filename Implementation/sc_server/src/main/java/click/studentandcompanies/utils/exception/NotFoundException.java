package click.studentandcompanies.utils.exception;

public class NotFoundException extends RuntimeException {

    /**
     * @param message Message to be communicated to the client
     * @throws NotFoundException when the requested resource is not found. It will return to the sender a 404 status code
     */
    public NotFoundException(String message) {
        super(message);
    }
}
