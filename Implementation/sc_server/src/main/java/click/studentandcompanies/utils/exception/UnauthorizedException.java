package click.studentandcompanies.utils.exception;

public class UnauthorizedException extends RuntimeException {
    /**
     * @param message`Message to be communicated to the client
     * @throws UnauthorizedException when the user is not authorized to access the resourceIt will return to the sender a 401 status code
    */
    public UnauthorizedException(String message) {
        super(message);
    }
}
