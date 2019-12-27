package myRetail.com.exception;

public class ProductDetailsNotFoundException extends RuntimeException {

    /**
     * default serial  version id
     */
    private static final long serialVersionUID = 1L;

    public ProductDetailsNotFoundException(String message) {
        super(message);
    }
}
