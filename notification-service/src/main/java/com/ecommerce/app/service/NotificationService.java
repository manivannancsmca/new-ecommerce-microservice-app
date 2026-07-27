package com.ecommerce.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.app.dto.SendNotificationRequest;
import com.ecommerce.app.entity.NotificationLog;
import com.ecommerce.app.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public NotificationLog sendNotification(SendNotificationRequest request) {
    
        // Log transaction and simulate transactional delivery (Email/SMS/Push)
        NotificationLog notification = new NotificationLog(
            request.userId(),
            request.type(),
            request.recipient(),
            request.payload()
        );
        return notificationRepository.save(notification);
        
    }

    public List<NotificationLog> findByUserId(Long userId) {
        
       List<NotificationLog> notificationLog =  notificationRepository.findByUserId(userId);

       if (notificationLog == null) {
        throw new RuntimeException("no data found for this user id : " + userId);
       }
       return notificationLog;
    }
}
