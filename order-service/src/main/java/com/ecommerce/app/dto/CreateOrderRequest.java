package com.ecommerce.app.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateOrderRequest(
    @NotNull Long userId,
    @NotNull Long productId,
    @NotNull Integer quantity,
    @NotNull BigDecimal totalAmount
) {}
