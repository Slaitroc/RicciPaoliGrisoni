package click.studentandcompanies.utils.exception;

public class WrongStateException extends RuntimeException {
    //This exception is thrown when a function is called in a wrong state (typically when a function is called multiple times)
    //It will return to the sender a 400 Bad request status code
    public WrongStateException(String message) {
        super(message);
    }
}
