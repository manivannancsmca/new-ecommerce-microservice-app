package com.ecommerce.app.event;
public record StockReservationFailedEvent(
    Long orderId,
    Long productId,
    String reason
) {}
