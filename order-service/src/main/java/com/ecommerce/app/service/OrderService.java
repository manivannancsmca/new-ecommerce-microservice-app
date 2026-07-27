package com.ecommerce.app.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.dto.CreateOrderRequest;
import com.ecommerce.app.entity.PurchaseOrder;
import com.ecommerce.app.event.OrderCreatedEvent;
import com.ecommerce.app.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public PurchaseOrder createOrder(CreateOrderRequest request) {
        // Step 1: Save order in PENDING state
        PurchaseOrder order = new PurchaseOrder(request.userId(), request.totalAmount());
        PurchaseOrder savedOrder = orderRepository.save(order);

        // Step 2: Publish OrderCreatedEvent to initiate Saga
        OrderCreatedEvent event = new OrderCreatedEvent(
            savedOrder.getId(),
            request.userId(),
            request.productId(),
            request.quantity(),
            request.totalAmount()
        );

        kafkaTemplate.send("order-created-topic", savedOrder.getId().toString(), event);
        return savedOrder;
    }

    @Transactional
    public void approveOrder(Long orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus("APPROVED");
            orderRepository.save(order);
        });
    }

    @Transactional
    public void cancelOrder(Long orderId, String reason) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus("CANCELLED");
            orderRepository.save(order);
        });
    }
}
