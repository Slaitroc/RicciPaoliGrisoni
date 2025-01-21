package click.studentandcompanies.utils.exception;

public class BadInputException extends RuntimeException {

    /**
     * @param message Message to be communicated to the client
     * @throws BadInputException when the input is not valid of post and put requests. It will return to the sender a 400 status code
     */
    public BadInputException(String message) {
        super(message);
    }
}
