package com.ecommerce.app.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProcessPaymentRequest(
    @NotNull Long orderId,
    @NotNull BigDecimal amount,
    @NotNull String paymentMethod
) {}
