package com.ecommerce.app.events;

public record StockReservedEvent(Long orderId, Long productId, Integer quantity) {}
