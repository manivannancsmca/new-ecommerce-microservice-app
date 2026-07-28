package com.ecommerce.app.event;

import java.math.BigDecimal;

public record PaymentSuccessEvent(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    String transactionRef
) {}