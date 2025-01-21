package click.studentandcompanies.utils.exception;

public class UnauthorizedException extends RuntimeException {
    //This exception is thrown when the user is not authorized to access the resource
    //It will return to the sender a 401 status code
    public UnauthorizedException(String message) {
        super(message);
    }
}
