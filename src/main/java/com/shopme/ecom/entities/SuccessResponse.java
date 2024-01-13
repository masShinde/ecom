package com.shopme.ecom.entities;

import com.shopme.ecom.enums.ResponseType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Builder
public class SuccessResponse {

    private long timestamp;

    private int statusCode;

    private String message;

    private ResponseType status;

    private HashMap<String, ?> data;

}
