package com.project.backend.controllers;

import com.project.backend.models.MarketPrice;
import com.project.backend.services.MarketPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market-price")
public class MarketPriceController {

    private final MarketPriceService service;

    public MarketPriceController(MarketPriceService service) {
        this.service = service;
    }

    // ✅ View all prices (Admin, Shopkeeper, Customer)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SHOPKEEPER','CUSTOMER')")
    public ResponseEntity<List<MarketPrice>> getAllPrices() {
        return ResponseEntity.ok(service.getAllMarketPrices());
    }

    // ✅ Force Refresh (Admin only)
    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> refreshPrices() {
        service.fetchAndSaveMarketPrices();
        return ResponseEntity.ok("Market prices refreshed successfully.");
    }
}
