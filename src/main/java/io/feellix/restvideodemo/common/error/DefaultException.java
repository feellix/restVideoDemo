package io.feellix.restvideodemo.common.error;

public class DefaultException extends RuntimeException {
    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
