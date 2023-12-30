package com.shopme.ecom.entities;

import com.shopme.ecom.enums.ResponseType;
import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CommonResponse {

    private long timestamp;

    private int statusCode;

    private String message;

    @Enumerated
    private ResponseType status;

    private HashMap<String, ?> data;

    public CommonResponse() {
    }

    public CommonResponse(long timestamp, int statusCode, String message, ResponseType status) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
    }

    public CommonResponse(long timestamp, int statusCode, String message, ResponseType status, HashMap<String, ?> data) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
        this.data = data;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, ?> getData(){
        return this.data;
    }

    public void setData(HashMap<String, ?> data){
        this.data = data;
    }

    public ResponseType getStatus() {
        return status;
    }

    public void setStatus(ResponseType status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "timestamp=" + timestamp +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
