package com.shopme.ecom.exceptionHandlers;

import com.shopme.ecom.entities.FailureResponse;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryInternalException;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryNotFoundException;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryUniqueException;
import com.shopme.ecom.utils.CommonUtilities;
import com.shopme.ecom.enums.ResponseType;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler {

    @Autowired
    private ResponseHandler responseHandler;

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleInternalException(CategoryInternalException ex){
        return responseHandler.handleResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleNotFoundException(CategoryNotFoundException ex){
        return responseHandler.handleResponse(HttpStatus.NOT_FOUND, ex);
    }


    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleUniqueCategoryException(CategoryUniqueException ex){
        return responseHandler.handleResponse(HttpStatus.BAD_REQUEST, ex);
    }

}
