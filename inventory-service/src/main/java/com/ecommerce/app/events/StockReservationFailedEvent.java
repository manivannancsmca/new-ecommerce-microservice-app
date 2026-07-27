package com.ecommerce.app.events;

public record StockReservationFailedEvent(Long orderId, Long productId, String reason) {}