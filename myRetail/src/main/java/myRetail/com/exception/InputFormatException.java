package myRetail.com.exception;

public class InputFormatException extends RuntimeException {

    /**
     * default serial  version id
     */
    private static final long serialVersionUID = 1L;

    public InputFormatException(String message) {
        super(message);
    }
}
