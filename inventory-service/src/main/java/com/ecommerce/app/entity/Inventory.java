package com.ecommerce.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "inventories")
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private Integer availableQty;

    @Column(nullable = false)
    private Integer reservedQty;

    @Column(nullable = false)
    private Instant updatedAt;

    public Inventory(Long productId, Long warehouseId, Integer availableQty, Integer reservedQty) {
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.availableQty = availableQty;
        this.reservedQty = reservedQty;
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    @PrePersist
    public void onSave() {
        this.updatedAt = Instant.now();
    }

}