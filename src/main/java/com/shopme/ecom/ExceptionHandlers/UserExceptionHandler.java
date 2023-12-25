package com.shopme.ecom.ExceptionHandlers;

import com.shopme.ecom.entities.CommonResponse;
import com.shopme.ecom.exceptions.userExceptions.UserAlreadyExistsException;
import com.shopme.ecom.exceptions.userExceptions.UserCreateException;
import com.shopme.ecom.exceptions.userExceptions.UserInternalException;
import com.shopme.ecom.exceptions.userExceptions.UserNotFoundException;
import com.shopme.ecom.utils.CommonUtilities;
import com.shopme.ecom.utils.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpHeaders;

@ControllerAdvice
public class UserExceptionHandler {

    @Autowired
    private CommonUtilities commonUtilities;

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleUserCreateException(UserCreateException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),  ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleUserNotFoundException(UserNotFoundException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CommonResponse> handleUserInternalExceptionHandler(UserInternalException ex){
        CommonResponse cr = new CommonResponse(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ResponseType.Failed);
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(cr, headers, HttpStatus.BAD_REQUEST);
    }


}
