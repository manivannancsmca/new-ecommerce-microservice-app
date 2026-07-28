package com.ecommerce.app.config;

import com.ecommerce.app.dto.ExceptionResponse;
import com.ecommerce.app.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionResponse message = null;

        // Try reading the JSON response body from the Product service
        try (InputStream bodyIs = response.body().asInputStream()) {
            message = objectMapper.readValue(bodyIs, ExceptionResponse.class);
        } catch (Exception e) {
            log.error("Error reading Feign error response body", e);
        }

        // Map HTTP 404 to ProductNotFoundException
        if (response.status() == 404) {
            String errorMessage = (message != null && message.getMessage() != null) 
                    ? message.getMessage() 
                    : "Product not found";
            
            // 🎯 Throwing your custom exception in the Order application!
            return new ProductNotFoundException(errorMessage);
        }

        // Fall back to standard Feign error decoder for other HTTP statuses (500, 503, etc.)
        return defaultErrorDecoder.decode(methodKey, response);
    }
}