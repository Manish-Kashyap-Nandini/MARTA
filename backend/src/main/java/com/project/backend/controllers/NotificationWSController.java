package com.project.backend.controllers;

import com.project.backend.controllers.dto.NotificationMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class NotificationWSController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationWSController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

   // @MessageMapping("/notify") // client SEND → /app/notify
    //public void notifyShopkeeper(NotificationMessage msg) {
        // broadcast to all subscribers
   //     messagingTemplate.convertAndSend("/topic/notifications", msg);
   // }
    
    @MessageMapping("/notify") // listens to SEND destination:/app/notify
    @SendTo("/topic/notifications") // broadcasts to subscribers
    public NotificationMessage broadcast(NotificationMessage message) {
        return message; // echoes back the same message
    }
    
    
    
}
