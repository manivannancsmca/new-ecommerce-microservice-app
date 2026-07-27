package com.ecommerce.app.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.dto.ProcessPaymentRequest;
import com.ecommerce.app.entity.PaymentTransaction;
import com.ecommerce.app.events.PaymentFailedEvent;
import com.ecommerce.app.events.PaymentSuccessEvent;
import com.ecommerce.app.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @SuppressWarnings("null")
    @Transactional
    public PaymentTransaction processPayment(ProcessPaymentRequest request) {

        String txRef = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        boolean isApproved = request.amount() != null && request.amount().compareTo(BigDecimal.ZERO) > 0;
        String status = isApproved ? "SUCCESS" : "FAILED";

        PaymentTransaction transaction = new PaymentTransaction(
                null,
                request.orderId(),
                request.amount(),
                request.paymentMethod(),
                status,
                txRef);

        PaymentTransaction savedTx = paymentRepository.save(transaction);

        if (isApproved) {
            PaymentSuccessEvent event = new PaymentSuccessEvent(
                    savedTx.getId(),
                    savedTx.getOrderId(),
                    savedTx.getAmount(),
                    savedTx.getTransactionRef());
            kafkaTemplate.send("payment-success-topic", savedTx.getOrderId().toString(), event);
        } else {
            PaymentFailedEvent event = new PaymentFailedEvent(request.orderId(),
                    "INSUFFICIENT_FUNDS_OR_INVALID_AMOUNT");
            kafkaTemplate.send("payment-failed-topic", request.orderId().toString(), event);
        }

        return savedTx;
    }

    public PaymentTransaction findByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("no data found : " + orderId));

    }
}
