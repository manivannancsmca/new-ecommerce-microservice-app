package com.ecommerce.app.event;

import java.math.BigDecimal;

public record ProductUpdatedEvent(
    Long id, 
    String name, 
    String sku, 
    BigDecimal price, 
    Long categoryId, 
    Boolean active) 
{}
