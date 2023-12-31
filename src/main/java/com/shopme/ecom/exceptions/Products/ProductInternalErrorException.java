package com.shopme.ecom.exceptions.Products;

public class ProductInternalErrorException extends RuntimeException{
    public ProductInternalErrorException() {
    }

    public ProductInternalErrorException(String message) {
        super(message);
    }

    public ProductInternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
