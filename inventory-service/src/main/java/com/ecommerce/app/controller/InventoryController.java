package com.ecommerce.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.entity.Inventory;
import com.ecommerce.app.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProduct(@PathVariable Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryRepository.save(inventory));
    }
}
