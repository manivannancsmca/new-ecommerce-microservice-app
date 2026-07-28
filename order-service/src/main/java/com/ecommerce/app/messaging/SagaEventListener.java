package com.ecommerce.app.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.app.event.PaymentFailedEvent;
import com.ecommerce.app.event.PaymentSuccessEvent;
import com.ecommerce.app.event.StockReservationFailedEvent;
import com.ecommerce.app.event.StockReservedEvent;
import com.ecommerce.app.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SagaEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "stock-reserved-topic", groupId = "order-saga-group")
    public void handleStockReserved(StockReservedEvent event) {
        // Proceed to next step in Saga (e.g., Payment or Approval)
        orderService.approveOrder(event.orderId());
    }

    @KafkaListener(topics = "stock-reservation-failed-topic", groupId = "order-saga-group")
    public void handleStockReservationFailed(StockReservationFailedEvent event) {
        // Execute compensating action: cancel order
        orderService.cancelOrder(event.orderId(), event.reason());
    }

    @KafkaListener(topics = "payment-success-topic", groupId = "order-saga-group")
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        // Execute compensating action: cancel order
        orderService.orderDeliveryCompleted(event);
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "order-saga-group")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        // Execute compensating action: cancel order
        orderService.orderDeliveryFailed(event);
    }
}
