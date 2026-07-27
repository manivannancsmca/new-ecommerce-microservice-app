package com.ecommerce.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "notification_logs")
@Data
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 30)
    private String type; // EMAIL, SMS, PUSH

    @Column(nullable = false, length = 150)
    private String recipient;

    @Column(nullable = false, length = 500)
    private String payload;

    @Column(nullable = false, updatable = false)
    private Instant sentAt;

    public NotificationLog() {}

    public NotificationLog(Long userId, String type, String recipient, String payload) {
        this.userId = userId;
        this.type = type;
        this.recipient = recipient;
        this.payload = payload;
        this.sentAt = Instant.now();
    }

}