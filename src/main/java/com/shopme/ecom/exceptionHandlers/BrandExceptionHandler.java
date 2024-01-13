package com.shopme.ecom.exceptionHandlers;


import com.shopme.ecom.entities.FailureResponse;
import com.shopme.ecom.enums.ResponseType;
import com.shopme.ecom.exceptions.Brand.BrandBadRequestException;
import com.shopme.ecom.exceptions.Brand.BrandInternalErrorException;
import com.shopme.ecom.exceptions.Brand.BrandNotFoundException;
import com.shopme.ecom.utils.CommonUtilities;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BrandExceptionHandler {

    @Autowired
    private ResponseHandler responseHandler;

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handlerInternalServerException(BrandInternalErrorException ex){
        return responseHandler.handleResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handlerInternalServerException(BrandNotFoundException ex){
        return responseHandler.handleResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handlerInternalServerException(BrandBadRequestException ex){
        return responseHandler.handleResponse(HttpStatus.BAD_REQUEST, ex);
    }


}
