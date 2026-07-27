CREATE TABLE notification_logs
(
    id BIGINT NOT NULL AUTO_INCREMENT,

    user_id BIGINT NOT NULL,

    type VARCHAR(30) NOT NULL,

    recipient VARCHAR(150) NOT NULL,

    payload VARCHAR(500) NOT NULL,

    sent_at TIMESTAMP(6) NOT NULL
        DEFAULT CURRENT_TIMESTAMP(6),

    PRIMARY KEY (id),

    INDEX idx_notification_user (user_id),

    INDEX idx_notification_type (type),

    INDEX idx_notification_sent_at (sent_at)

) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;