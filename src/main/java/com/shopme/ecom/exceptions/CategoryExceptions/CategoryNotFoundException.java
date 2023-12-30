package com.shopme.ecom.exceptions.CategoryExceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
