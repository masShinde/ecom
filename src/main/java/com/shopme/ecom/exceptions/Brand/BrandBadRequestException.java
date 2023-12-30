package com.shopme.ecom.exceptions.Brand;

public class BrandBadRequestException extends RuntimeException{

    public BrandBadRequestException() {
    }

    public BrandBadRequestException(String message) {
        super(message);
    }

    public BrandBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
