package myRetail.com.exception;

public class ProductIdMismatchException extends RuntimeException {

    /**
     * default serial  version id
     */
    private static final long serialVersionUID = 1L;

    public ProductIdMismatchException(String message) {
        super(message);
    }
}
