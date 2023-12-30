package com.shopme.ecom.exceptions.CommonExceptions;

public class InvalidImageException extends RuntimeException{

    public InvalidImageException() {
    }

    public InvalidImageException(String message) {
        super(message);
    }

    public InvalidImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
