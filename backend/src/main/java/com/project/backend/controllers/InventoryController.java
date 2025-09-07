package com.project.backend.controllers;

import com.project.backend.controllers.dto.InventoryRequest;
import com.project.backend.models.Inventory;
import com.project.backend.services.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // ➕ increase stock by delta
    @PostMapping("/add")
    public ResponseEntity<Inventory> add(@RequestBody InventoryRequest req) {
        Inventory updated = service.addStock(req.getProductId(), req.getQuantity());
        return ResponseEntity.ok(updated);
    }

    // = set absolute quantity
    @PutMapping("/set")
    public ResponseEntity<Inventory> set(@RequestBody InventoryRequest req) {
        Inventory updated = service.setStock(req.getProductId(), req.getQuantity());
        return ResponseEntity.ok(updated);
    }

    // 📋 get all inventory records
    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        List<Inventory> allInventory = service.getAllInventory();
        return ResponseEntity.ok(allInventory);
    }

    // 🔎 get current quantity
    @GetMapping("/{productId}")
    public ResponseEntity<?> get(@PathVariable Long productId) {
        int qty = service.getStock(productId);
        return ResponseEntity.ok(Map.of(
                "productId", productId,
                "quantity", qty
        ));
    }

    // 🗑️ remove inventory row for a product
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<?> delete(@PathVariable Long inventoryId) {
        service.deleteByInventoryId(inventoryId);
        return ResponseEntity.ok(Map.of("deleted", true));
    }

}
