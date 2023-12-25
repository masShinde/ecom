package com.shopme.ecom.utils;

import com.shopme.ecom.entities.User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AbstractExporter {
    public void setResponseHeaders(HttpServletResponse response, String contentType, String extension) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = format.format(new Date());
        String fileName = "users_" + timeStamp + extension;

        response.setContentType(contentType);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
    }
}
