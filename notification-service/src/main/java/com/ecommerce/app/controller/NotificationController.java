package com.ecommerce.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.app.dto.SendNotificationRequest;
import com.ecommerce.app.entity.NotificationLog;
import com.ecommerce.app.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationLog> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        NotificationLog notification = notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationLog>> getNotificationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.findByUserId(userId));
    }
}
