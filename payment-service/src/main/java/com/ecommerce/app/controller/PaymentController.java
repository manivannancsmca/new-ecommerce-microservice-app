package com.ecommerce.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.dto.ProcessPaymentRequest;
import com.ecommerce.app.entity.PaymentTransaction;
import com.ecommerce.app.repository.PaymentRepository;
import com.ecommerce.app.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<PaymentTransaction> processPayment(@Valid @RequestBody ProcessPaymentRequest request) {
        PaymentTransaction transaction = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentTransaction> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentTransaction transaction = paymentService.findByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
