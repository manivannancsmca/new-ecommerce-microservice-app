package com.ecommerce.app.service;

import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.AddItemRequest;
import com.ecommerce.app.dto.CartSummaryResponse;
import com.ecommerce.app.entity.CartItem;
import com.ecommerce.app.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    @Value("${cart.tax-rate:0.10}") // Default 10% tax rate
    private BigDecimal taxRate;

    public CartItem addItemToCart(AddItemRequest request) {
        String compositeId = request.userId() + ":" + request.productId();
        Optional<CartItem> existingItemOpt = cartRepository.findById(compositeId);

        CartItem item;
        if (existingItemOpt.isPresent()) {
            item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            item.setUnitPrice(request.unitPrice()); // Update to current price
        } else {
            item = new CartItem(request.userId(), request.productId(), request.quantity(), request.unitPrice());
        }

        return cartRepository.save(item);
    }

    public void updateQuantity(Long userId, Long productId, Integer quantity) {
        String compositeId = userId + ":" + productId;
        Optional<CartItem> existingItemOpt = cartRepository.findById(compositeId);

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            if (quantity <= 0) {
                cartRepository.delete(item);
            } else {
                item.setQuantity(quantity);
                cartRepository.save(item);
            }
        }
    }

    public void removeItemFromCart(Long userId, Long productId) {
        String compositeId = userId + ":" + productId;
        cartRepository.deleteById(compositeId);
    }

    public List<CartItem> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(items);
    }

    public CartSummaryResponse getCartSummary(Long userId, BigDecimal discountPercentage) {
        List<CartItem> items = getCartItems(userId);

        BigDecimal subtotal = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal discountPct = (discountPercentage != null) ? discountPercentage : BigDecimal.ZERO;
        BigDecimal discountAmount = subtotal.multiply(discountPct)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal taxableAmount = subtotal.subtract(discountAmount);
        BigDecimal taxAmount = taxableAmount.multiply(taxRate)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal grandTotal = taxableAmount.add(taxAmount)
                .setScale(2, RoundingMode.HALF_UP);

        return new CartSummaryResponse(userId, items, subtotal, discountAmount, taxAmount, grandTotal);
    }
}