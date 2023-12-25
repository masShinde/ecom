package com.shopme.ecom.utils;

import org.springframework.stereotype.Component;

import org.springframework.http.HttpHeaders;

@Component
public class CommonUtilities {


    public HttpHeaders getCommonHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }


}
