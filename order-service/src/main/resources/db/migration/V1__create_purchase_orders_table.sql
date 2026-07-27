CREATE TABLE purchase_orders
(
    id BIGINT NOT NULL AUTO_INCREMENT,

    user_id BIGINT NOT NULL,

    total_amount DECIMAL(10,2) NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    payment_id VARCHAR(100),

    created_at TIMESTAMP(6) NOT NULL
        DEFAULT CURRENT_TIMESTAMP(6),

    PRIMARY KEY (id),

    INDEX idx_purchase_orders_user (user_id),

    INDEX idx_purchase_orders_status (status),

    INDEX idx_purchase_orders_created_at (created_at),

    INDEX idx_purchase_orders_payment (payment_id)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;