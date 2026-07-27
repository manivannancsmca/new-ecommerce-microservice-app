CREATE TABLE payment_transactions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL,
    transaction_ref VARCHAR(100) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uk_payment_transaction_ref UNIQUE (transaction_ref)
);