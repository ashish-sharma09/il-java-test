package co.uk.henry.product;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
