package com.ecommerce.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.app.document.ProductDocument;
import com.ecommerce.app.event.ProductCreatedEvent;
import com.ecommerce.app.event.ProductDeletedEvent;
import com.ecommerce.app.event.ProductUpdatedEvent;
import com.ecommerce.app.repository.ProductSearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSyncService {

    private final ProductSearchRepository repository;

    public void handleCreated(ProductUpdatedEvent event) {
        String docId = String.valueOf(event.getId());
        log.info("docId : {} ", docId);
        // Idempotency check: Ignore if an equal or newer record exists
        // if (isStale(docId, event.getVersion())) {
        //     log.warn("Skipping stale Created event for productId={}", event.getProductId());
        //     return;
        // }

        ProductDocument doc = ProductDocument.builder()
                .id(docId)
                .name(event.getName())
                .sku(event.getSku())
                .price(event.getPrice())
                .categoryId(event.getCategoryId())
                .active(event.getActive())
                //.version(event.getVersion() != null ? event.getVersion() : System.currentTimeMillis())
                .build();

        repository.save(doc);
        log.info("Indexed product in Elasticsearch: {}", docId);
    }

    // public void handleUpdated(ProductUpdatedEvent event) {
    //     String docId = String.valueOf(event.getProductId());

    //     if (isStale(docId, event.getVersion())) {
    //         log.warn("Skipping stale Updated event for productId={}", event.getProductId());
    //         return;
    //     }

    //     ProductDocument doc = ProductDocument.builder()
    //             .id(docId)
    //             .name(event.getName())
    //             .sku(event.getSku())
    //             .price(event.getPrice())
    //             .categoryId(event.getCategoryId())
    //             .active(event.getActive())
    //             .version(event.getVersion() != null ? event.getVersion() : System.currentTimeMillis())
    //             .build();

    //     repository.save(doc);
    //     log.info("Updated product in Elasticsearch: {}", docId);
    // }

    public void handleDeleted(ProductDeletedEvent event) {
        String docId = String.valueOf(event.getProductId());
        if (repository.existsById(docId)) {
            repository.deleteById(docId);
            log.info("Deleted product from Elasticsearch: {}", docId);
        }
    }

    // private boolean isStale(String docId, Long newVersion) {
    //     if (newVersion == null) return false;
    //     Optional<ProductDocument> existing = repository.findById(docId);
    //     return existing.map(doc -> doc.getVersion() != null && doc.getVersion() >= newVersion).orElse(false);
    // }
}
