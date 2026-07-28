package com.ecommerce.app.event;

public record PaymentFailedEvent(
    Long orderId,
    String reason
) {}
