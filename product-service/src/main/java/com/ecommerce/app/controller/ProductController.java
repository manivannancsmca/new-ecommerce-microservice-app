package com.ecommerce.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.entity.Product;
import com.ecommerce.app.service.ProductService;

import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Product request) {

        productService.createOrUpdateProduct(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Product created successfully");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable long productId) {
        log.info("productId : ", productId);
       Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
    }
}
