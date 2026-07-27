package com.ecommerce.app.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ProductCreatedEvent extends BaseProductEvent {
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private Boolean active;
}