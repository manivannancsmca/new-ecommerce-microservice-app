package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.entity.PurchaseOrder;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    /**
     * Retrieves all orders associated with a specific user.
     */
    List<PurchaseOrder> findByUserId(Long userId);
}
