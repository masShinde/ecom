package com.shopme.ecom.exceptions.Brand;


public class BrandInternalErrorException extends RuntimeException {

    public BrandInternalErrorException() {
    }

    public BrandInternalErrorException(String message) {
        super(message);
    }

    public BrandInternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
