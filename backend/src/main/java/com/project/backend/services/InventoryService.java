package com.project.backend.services;

import com.project.backend.models.Inventory;
import com.project.backend.models.Product;
import com.project.backend.repositories.InventoryRepository;
import com.project.backend.repositories.ProductRepository;
import com.project.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;
    private final ProductRepository productRepo;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public InventoryService(InventoryRepository inventoryRepo,
                            ProductRepository productRepo,
                            NotificationService notificationService,
                            UserRepository userRepository) {
        this.inventoryRepo = inventoryRepo;
        this.productRepo = productRepo;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    // ➕ increase stock by delta
    @Transactional
    public Inventory addStock(Long productId, int delta) {
        Inventory inv = inventoryRepo.findByProductId(productId)
                .orElseGet(() -> {
                    Product product = productRepo.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    Inventory newInv = new Inventory();
                    newInv.setProduct(product);
                    return newInv;
                });
        inv.setQuantity(inv.getQuantity() + delta);
        Inventory saved = inventoryRepo.save(inv);

        notifyIfLowStock(saved.getProduct(), saved.getQuantity());
        return saved;
    }

    // = set absolute quantity
    @Transactional
    public Inventory setStock(Long productId, int qty) {
        Inventory inv = inventoryRepo.findByProductId(productId)
                .orElseGet(() -> {
                    Product product = productRepo.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    Inventory newInv = new Inventory();
                    newInv.setProduct(product);
                    return newInv;
                });
        inv.setQuantity(qty);
        Inventory saved = inventoryRepo.save(inv);

        notifyIfLowStock(saved.getProduct(), saved.getQuantity());
        return saved;
    }

    // 🔎 get stock quantity for a product
    @Transactional(readOnly = true)
    public int getStock(Long productId) {
        return inventoryRepo.findByProductId(productId)
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    // 🗑️ delete inventory row by product
    @Transactional
    public void deleteByInventoryId(Long inventoryId) {
        inventoryRepo.deleteByInventoryId(inventoryId);
    }

    // 📋 get all inventory
    @Transactional(readOnly = true)
    public List<Inventory> getAllInventory() {
        return inventoryRepo.findAll();
    }

    // 📋 get all inventory for a shopkeeper
    @Transactional(readOnly = true)
    public List<Inventory> getInventoryForShopkeeper(String shopkeeperEmail) {
        return inventoryRepo.findByProduct_ShopkeeperEmail(shopkeeperEmail);
    }

    // 🔔 helper: notify shopkeeper if stock is low
    private void notifyIfLowStock(Product product, int quantity) {
        int THRESHOLD = 5; // adjust as needed
        if (quantity < THRESHOLD) {
            Long shopkeeperId = userRepository.findByEmail(product.getShopkeeperEmail())
                    .map(u -> u.getId())
                    .orElse(null);
            if (shopkeeperId != null) {
                notificationService.sendToShopkeeper(
                        shopkeeperId,
                        "Low Stock Alert",
                        "Product '" + product.getName() + "' has only " + quantity + " left."
                );
            }
        }
    }
}
