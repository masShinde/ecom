package com.shopme.ecom.exceptionHandlers;

import com.shopme.ecom.entities.FailureResponse;
import com.shopme.ecom.exceptions.userExceptions.UserAlreadyExistsException;
import com.shopme.ecom.exceptions.userExceptions.UserCreateException;
import com.shopme.ecom.exceptions.userExceptions.UserInternalException;
import com.shopme.ecom.exceptions.userExceptions.UserNotFoundException;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpHeaders;

@ControllerAdvice
public class UserExceptionHandler {

    @Autowired
    private ResponseHandler responseHandler;

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleUserCreateException(UserCreateException ex){
        return responseHandler.handleResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleUserNotFoundException(UserNotFoundException ex){
        return responseHandler.handleResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        return responseHandler.handleResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleUserInternalExceptionHandler(UserInternalException ex){
        return responseHandler.handleResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }


}
