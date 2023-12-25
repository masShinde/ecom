package com.shopme.ecom.exceptions.userExceptions;

public class UserCreateException extends RuntimeException{

    public UserCreateException(String message) {
        super(message);
    }

    public UserCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCreateException(Throwable cause) {
        super(cause);
    }
}
