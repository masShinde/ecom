package com.shopme.ecom.ExceptionHandlers;

import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryInternalException;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryNotFoundException;
import com.shopme.ecom.exceptions.CategoryExceptions.CategoryUniqueException;
import com.shopme.ecom.utils.CommonUtilities;
import com.shopme.ecom.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler {

    @Autowired
    private CommonUtilities commonUtilities;

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleInternalException(CategoryInternalException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleNotFoundException(CategoryNotFoundException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleUniqueCategoryException(CategoryUniqueException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.BAD_REQUEST);
    }

}
