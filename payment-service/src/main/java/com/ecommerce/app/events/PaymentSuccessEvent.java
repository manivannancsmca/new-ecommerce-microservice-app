package com.ecommerce.app.events;

import java.math.BigDecimal;

public record PaymentSuccessEvent(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    String transactionRef
) {}