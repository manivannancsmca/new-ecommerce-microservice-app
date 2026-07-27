package com.ecommerce.app.event;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdatedEvent {
    Long id;
    String name; 
    String sku;
    BigDecimal price; 
    Long categoryId;
    Boolean active;    
}
