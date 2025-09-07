package com.project.backend.controllers;

import com.project.backend.controllers.dto.ConversationRequest;
import com.project.backend.models.Conversation;
import com.project.backend.services.ConversationService;
import com.project.backend.controllers.dto.RealtimeMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ConversationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConversationService conversationService;

    public ConversationController(SimpMessagingTemplate messagingTemplate,
                                  ConversationService conversationService) {
        this.messagingTemplate = messagingTemplate;
        this.conversationService = conversationService;
    }

    // Endpoint for sending a new chat message via WebSocket
    @MessageMapping("/conversation.send")
    public void sendMessage(ConversationRequest dto) {
        Conversation saved = conversationService.saveMessage(dto);

        // Wrap as CHAT message
        RealtimeMessage chatMessage = new RealtimeMessage("CHAT", saved);

        // Send to receiver
        messagingTemplate.convertAndSend(
                "/topic/user/" + dto.getReceiverId(),
                chatMessage
        );

        // Also send to sender
        messagingTemplate.convertAndSend(
                "/topic/user/" + dto.getSenderId(),
                chatMessage
        );
    }
}
