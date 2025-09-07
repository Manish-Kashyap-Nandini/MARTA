package com.project.backend.controllers.dto;

public class InventoryRequest {
    private Long productId;
    private int quantity; // for /add: delta; for /set: absolute

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
