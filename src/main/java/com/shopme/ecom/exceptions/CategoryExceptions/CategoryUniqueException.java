package com.shopme.ecom.exceptions.CategoryExceptions;

public class CategoryUniqueException extends RuntimeException {

    public CategoryUniqueException() {
    }

    public CategoryUniqueException(String message) {
        super(message);
    }

    public CategoryUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
