package com.ecommerce.app.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.app.dto.SendNotificationRequest;
import com.ecommerce.app.events.OrderCreatedEvent;
import com.ecommerce.app.events.PaymentFailedEvent;
import com.ecommerce.app.events.PaymentSuccessEvent;
import com.ecommerce.app.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "order-created-topic", groupId = "notification-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        SendNotificationRequest request = new SendNotificationRequest(
            event.userId(),
            "EMAIL",
            "user-" + event.userId() + "@example.com",
            "Your order #" + event.orderId() + " has been placed successfully."
        );
        notificationService.sendNotification(request);
    }

    @KafkaListener(topics = "payment-success-topic", groupId = "notification-group")
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        SendNotificationRequest request = new SendNotificationRequest(
            1001L, // Sample user mapping
            "SMS",
            "+1234567890",
            "Payment successful for Order #" + event.orderId() + ". Ref: " + event.transactionRef()
        );
        notificationService.sendNotification(request);
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "notification-group")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        SendNotificationRequest request = new SendNotificationRequest(
            1001L,
            "EMAIL",
            "user-failed@example.com",
            "Payment failed for Order #" + event.orderId() + ". Reason: " + event.reason()
        );
        notificationService.sendNotification(request);
    }
}
