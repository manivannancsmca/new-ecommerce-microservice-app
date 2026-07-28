package com.ecommerce.app.dto;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String message;
    private int status;
    private String timestamp;
}
