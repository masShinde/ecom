package com.shopme.ecom.exceptions.Products;

public class ProductBadRequestException extends RuntimeException{

    public ProductBadRequestException() {
    }

    public ProductBadRequestException(String message) {
        super(message);
    }

    public ProductBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
