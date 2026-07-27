package com.ecommerce.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.entity.CartItem;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<CartItem, String> {
    List<CartItem> findByUserId(Long userId);
}
