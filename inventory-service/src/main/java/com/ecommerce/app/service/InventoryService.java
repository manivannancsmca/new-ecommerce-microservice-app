package com.ecommerce.app.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.entity.Inventory;
import com.ecommerce.app.events.OrderCreatedEvent;
import com.ecommerce.app.events.StockReservationFailedEvent;
import com.ecommerce.app.events.StockReservedEvent;
import com.ecommerce.app.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void reserveStock(OrderCreatedEvent event) {

        Inventory inventory = inventoryRepository.findByProductId(event.productId()).orElse(null);

        if (inventory == null || inventory.getAvailableQty() < event.quantity()) {

            // Emit Failure Event to Kafka
            StockReservationFailedEvent failEvent = new StockReservationFailedEvent(
                event.orderId(), event.productId(), "INSUFFICIENT_STOCK"
            );
            kafkaTemplate.send("stock-reservation-failed-topic", event.orderId().toString(), failEvent);
            return;
        }

        // Deduct available and increase reserved quantity
        inventory.setAvailableQty(inventory.getAvailableQty() - event.quantity());
        inventory.setReservedQty(inventory.getReservedQty() + event.quantity());
        inventoryRepository.save(inventory);

        // Emit Stock Reserved Event to Kafka
        StockReservedEvent reservedEvent = new StockReservedEvent(
            event.orderId(), event.productId(), event.quantity()
        );
        kafkaTemplate.send("stock-reserved-topic", event.orderId().toString(), reservedEvent);
    }

}
