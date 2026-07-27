package com.ecommerce.app.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.app.entity.CartItem;

public record CartSummaryResponse(
    Long userId,
    List<CartItem> items,
    BigDecimal subtotal,
    BigDecimal discountAmount,
    BigDecimal taxAmount,
    BigDecimal grandTotal
) {}
