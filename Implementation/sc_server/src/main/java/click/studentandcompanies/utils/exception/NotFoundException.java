package click.studentandcompanies.utils.exception;

public class NotFoundException extends RuntimeException {
    //This exception is thrown when the requested resource is not found
    //It will return to the sender a 404 status code
    public NotFoundException(String message) {
        super(message);
    }
}
