package com.ecommerce.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecommerce.app.event.BaseProductEvent;
import com.ecommerce.app.event.ProductCreatedEvent;
import com.ecommerce.app.event.ProductDeletedEvent;
import com.ecommerce.app.event.ProductUpdatedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumerService {

    private final ProductSyncService syncService;

    @KafkaListener(
        topics = "${app.kafka.topics.product-events}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(BaseProductEvent event) {
        log.info("Received event type {} for product id {}", event.getClass().getSimpleName(), event.getProductId());

        if (event instanceof ProductCreatedEvent createdEvent) {
            syncService.handleCreated(createdEvent);
        } else if (event instanceof ProductUpdatedEvent updatedEvent) {
            syncService.handleUpdated(updatedEvent);
        } else if (event instanceof ProductDeletedEvent deletedEvent) {
            syncService.handleDeleted(deletedEvent);
        } else {
            log.warn("Unknown event type encountered: {}", event.getClass());
        }
    }
}
