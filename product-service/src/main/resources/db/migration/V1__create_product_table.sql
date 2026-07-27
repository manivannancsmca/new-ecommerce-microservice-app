CREATE TABLE products
(
    id BIGINT NOT NULL AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    sku VARCHAR(50) NOT NULL,

    price DECIMAL(10,2) NOT NULL,

    category_id BIGINT NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    PRIMARY KEY (id),

    CONSTRAINT uk_product_sku UNIQUE (sku),

    INDEX idx_product_category(category_id),

    INDEX idx_product_active(active)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;