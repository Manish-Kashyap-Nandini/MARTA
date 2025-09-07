package com.project.backend.controllers;

import com.project.backend.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shop/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Simple test: send a notification to myself (must be SHOPKEEPER)
    @PostMapping("/test")
    public ResponseEntity<?> sendTest(@RequestParam Long shopkeeperId,
                                      @RequestParam String title,
                                      @RequestParam String body,
                                      Authentication auth) {
        // You can also enforce that auth user id == shopkeeperId, if desired.
        notificationService.sendToShopkeeper(shopkeeperId, title, body);
        return ResponseEntity.ok(Map.of("sent", true));
    }
    
    
    
    
}
