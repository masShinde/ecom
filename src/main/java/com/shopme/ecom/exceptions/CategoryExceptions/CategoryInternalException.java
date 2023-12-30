package com.shopme.ecom.exceptions.CategoryExceptions;

public class CategoryInternalException extends RuntimeException {

    public CategoryInternalException() {
    }

    public CategoryInternalException(String message) {
        super(message);
    }

    public CategoryInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
