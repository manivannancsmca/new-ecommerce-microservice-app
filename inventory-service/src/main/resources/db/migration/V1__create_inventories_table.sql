CREATE TABLE inventories
(
    id BIGINT NOT NULL AUTO_INCREMENT,

    product_id BIGINT NOT NULL,

    warehouse_id BIGINT NOT NULL,

    available_qty INT NOT NULL,

    reserved_qty INT NOT NULL DEFAULT 0,

    updated_at TIMESTAMP(6) NOT NULL
        DEFAULT CURRENT_TIMESTAMP(6)
        ON UPDATE CURRENT_TIMESTAMP(6),

    PRIMARY KEY (id),

    CONSTRAINT uk_inventory_product UNIQUE (product_id),

    INDEX idx_inventory_warehouse (warehouse_id),

    INDEX idx_inventory_updated_at (updated_at)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;