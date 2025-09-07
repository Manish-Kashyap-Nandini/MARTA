package com.project.backend.controllers;

import com.project.backend.models.Conversation;
import com.project.backend.services.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationRestController {

    private final ConversationService conversationService;

    public ConversationRestController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/history/{user1}/{user2}")
    public ResponseEntity<List<Conversation>> getHistory(
            @PathVariable Long user1, @PathVariable Long user2) {
        return ResponseEntity.ok(conversationService.getConversationHistory(user1, user2));
    }
}
