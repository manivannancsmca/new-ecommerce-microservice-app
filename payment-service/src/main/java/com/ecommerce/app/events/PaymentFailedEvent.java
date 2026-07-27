package com.ecommerce.app.events;

public record PaymentFailedEvent(
    Long orderId,
    String reason
) {}