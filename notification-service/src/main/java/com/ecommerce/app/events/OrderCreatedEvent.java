package com.ecommerce.app.events;

import java.math.BigDecimal;

public record OrderCreatedEvent(
    Long orderId,
    Long userId,
    Long productId,
    Integer quantity,
    BigDecimal totalAmount
) {}
