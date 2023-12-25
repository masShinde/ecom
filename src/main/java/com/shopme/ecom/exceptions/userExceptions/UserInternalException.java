package com.shopme.ecom.exceptions.userExceptions;

public class UserInternalException extends RuntimeException {

    public UserInternalException(String message) {
        super(message);
    }

    public UserInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
