package com.ecommerce.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.app.entity.Product;
import com.ecommerce.app.event.ProductUpdatedEvent;
import com.ecommerce.app.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Value("${app.kafka.topics.product-events}")
    private String productTopic;

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Product createOrUpdateProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        
        // Emit Event to Kafka for CQRS indexing in Elasticsearch
        ProductUpdatedEvent event = new ProductUpdatedEvent(
            savedProduct.getId(),
            savedProduct.getName(),
            savedProduct.getSku(),
            savedProduct.getPrice(),
            savedProduct.getCategoryId(),
            savedProduct.getActive()
        );

        kafkaTemplate.send(productTopic, savedProduct.getSku(), event);

        return savedProduct;
    }

    public Product getProductById(long productId) {
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("product is not found"));

       return product;
    }

}
