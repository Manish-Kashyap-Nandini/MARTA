package com.project.backend.services;

import com.project.backend.controllers.dto.NotificationMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.project.backend.controllers.dto.RealtimeMessage;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

   
    public void sendToShopkeeper(Long shopkeeperId, String title, String body) {
        NotificationMessage payload = new NotificationMessage(title, body);

        // Wrap as a NOTIFICATION type
        RealtimeMessage notification = new RealtimeMessage("NOTIFICATION", payload);

        // Send on unified per-user channel
        messagingTemplate.convertAndSend("/topic/user/" + shopkeeperId, notification);
    }

}



















