package com.ecommerce.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.AddItemRequest;
import com.ecommerce.app.dto.CartSummaryResponse;
import com.ecommerce.app.entity.CartItem;
import com.ecommerce.app.service.CartService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartItem> addItem(@Valid @RequestBody AddItemRequest request) {
        CartItem addedItem = cartService.addItemToCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartSummaryResponse> getCart(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0.0") BigDecimal discountPercentage) {
        CartSummaryResponse summary = cartService.getCartSummary(userId, discountPercentage);
        return ResponseEntity.ok(summary);
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        cartService.updateQuantity(userId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
