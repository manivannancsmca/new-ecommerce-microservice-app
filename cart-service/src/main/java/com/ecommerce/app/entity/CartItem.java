package com.ecommerce.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@RedisHash("cart_items")
public class CartItem implements Serializable {

    @Id
    private String cartId; // Composite key: {userId}:{productId}

    @Indexed
    private Long userId;

    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Instant addedAt;

    public CartItem() {}

    public CartItem(Long userId, Long productId, Integer quantity, BigDecimal unitPrice) {
        this.cartId = userId + ":" + productId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.addedAt = Instant.now();
    }

    // Getters and Setters
    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public Instant getAddedAt() { return addedAt; }
    public void setAddedAt(Instant addedAt) { this.addedAt = addedAt; }
}
