package com.shopme.ecom.exceptions.Brand;

public class BrandNotFoundException extends RuntimeException{

    public BrandNotFoundException() {
    }

    public BrandNotFoundException(String message) {
        super(message);
    }

    public BrandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
