package com.example.demo.exception;

/**
 * @author Geonguk Han
 * @since 2020-05-08
 */
public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
