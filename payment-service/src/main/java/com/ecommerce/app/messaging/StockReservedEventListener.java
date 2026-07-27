package com.ecommerce.app.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.app.dto.ProcessPaymentRequest;
import com.ecommerce.app.events.StockReservedEvent;
import com.ecommerce.app.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockReservedEventListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "stock-reserved-topic", groupId = "payment-group")
    public void handleStockReserved(StockReservedEvent event) {
        // Automatically trigger payment processing upon successful stock reservation
        ProcessPaymentRequest request = new ProcessPaymentRequest(
            event.orderId(),
            event.totalAmount() != null ? event.totalAmount() : java.math.BigDecimal.valueOf(100.00),
            "CREDIT_CARD"
        );
        paymentService.processPayment(request);
    }
}