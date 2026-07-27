package com.ecommerce.app.events;

import java.math.BigDecimal;

public record StockReservedEvent(
    Long orderId,
    Long productId,
    Integer quantity,
    BigDecimal totalAmount
) {}
