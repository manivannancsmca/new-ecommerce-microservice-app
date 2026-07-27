package com.ecommerce.app.event;

public record StockReservedEvent(
    Long orderId,
    Long productId,
    Integer quantity
) {}