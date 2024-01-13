package com.shopme.ecom.utils;


import com.shopme.ecom.entities.FailureResponse;
import com.shopme.ecom.entities.SuccessResponse;
import com.shopme.ecom.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResponseHandler {

    @Autowired
    private  CommonUtilities commonUtilities;

    public  ResponseEntity<SuccessResponse> handleResponse(HttpStatus status, HashMap<String, ?> data, String message){
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(ResponseType.Sucess)
                .statusCode(status.value())
                .timestamp(System.currentTimeMillis())
                .data(data)
                .message(message)
                .build();
        return new ResponseEntity<>(successResponse, status);
    }

    public ResponseEntity<FailureResponse> handleResponse(HttpStatus status, Exception ex){
        FailureResponse failureResponse = FailureResponse.builder()
                .statusCode(status.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .status(ResponseType.Failed)
                .build();
        HttpHeaders headers = commonUtilities.getCommonHeaders();
        return new ResponseEntity<>(failureResponse, headers, status);
    }

}
