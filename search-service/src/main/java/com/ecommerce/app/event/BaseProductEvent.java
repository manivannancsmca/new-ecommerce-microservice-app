package com.ecommerce.app.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "eventType",
    visible = true // <-- REQUIRED: Ensures Jackson reads and writes the property explicitly
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ProductCreatedEvent.class, name = "PRODUCT_CREATED"),
    @JsonSubTypes.Type(value = ProductUpdatedEvent.class, name = "PRODUCT_UPDATED"),
    @JsonSubTypes.Type(value = ProductDeletedEvent.class, name = "PRODUCT_DELETED")
})
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseProductEvent {
    private Long productId;
    private String sku;
    private Instant timestamp;
    private Long version;
}