package com.ecommerce.app.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerce.app.events.OrderCreatedEvent;
import com.ecommerce.app.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-created-topic", groupId = "inventory-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        inventoryService.reserveStock(event);
    }
}
