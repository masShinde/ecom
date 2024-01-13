package com.shopme.ecom.ExceptionHandlers;

import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.enums.ResponseType;
import com.shopme.ecom.exceptions.Products.ProductAlreadyExistsException;
import com.shopme.ecom.exceptions.Products.ProductBadRequestException;
import com.shopme.ecom.exceptions.Products.ProductInternalErrorException;
import com.shopme.ecom.exceptions.Products.ProductNotFoundException;
import com.shopme.ecom.utils.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandlers {

    @Autowired
    private CommonUtilities commonUtilities;

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handlerInternalException(ProductInternalErrorException ex){
        CommonResponse commonResponse = new CommonResponse(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ResponseType.Failed);
        return new ResponseEntity<>(commonResponse, commonUtilities.getCommonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler
    public ResponseEntity<CommonResponse> handlerInternalException(ProductNotFoundException ex){
        CommonResponse commonResponse = new CommonResponse(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), ResponseType.Failed);
        return new ResponseEntity<>(commonResponse, commonUtilities.getCommonHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex){
        CommonResponse commonResponse = new CommonResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ResponseType.Failed);
        return new ResponseEntity<>(commonResponse, commonUtilities.getCommonHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleProductBadRequestException(ProductBadRequestException ex){
        CommonResponse commonResponse = new CommonResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ResponseType.Failed);
        return new ResponseEntity<>(commonResponse, commonUtilities.getCommonHeaders(), HttpStatus.BAD_REQUEST);
    }

}
