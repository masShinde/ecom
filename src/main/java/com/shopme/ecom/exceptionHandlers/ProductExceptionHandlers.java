package com.shopme.ecom.exceptionHandlers;

import com.shopme.ecom.entities.FailureResponse;
import com.shopme.ecom.enums.ResponseType;
import com.shopme.ecom.exceptions.Products.ProductAlreadyExistsException;
import com.shopme.ecom.exceptions.Products.ProductBadRequestException;
import com.shopme.ecom.exceptions.Products.ProductInternalErrorException;
import com.shopme.ecom.exceptions.Products.ProductNotFoundException;
import com.shopme.ecom.utils.CommonUtilities;
import com.shopme.ecom.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandlers {

    @Autowired
    private ResponseHandler responseHandler;

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handlerInternalException(ProductInternalErrorException ex){
        return responseHandler.handleResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }


    @ExceptionHandler
    public ResponseEntity<FailureResponse> handlerInternalException(ProductNotFoundException ex){
        return responseHandler.handleResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex){
        return responseHandler.handleResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleProductBadRequestException(ProductBadRequestException ex){
        return responseHandler.handleResponse(HttpStatus.BAD_REQUEST, ex);
    }

}
