package com.project.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    private String description;

    private String category;

    // Who owns this product (shopkeeper’s email)
    @Column(nullable = false)
    private String shopkeeperEmail;
    
    
    @Column(name = "image_url")
    private String imageUrl;

    public Product() {}

    public Product(String name, Double price, String description, String category, String shopkeeperEmail) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.shopkeeperEmail = shopkeeperEmail;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getShopkeeperEmail() { return shopkeeperEmail; }
    public void setShopkeeperEmail(String shopkeeperEmail) { this.shopkeeperEmail = shopkeeperEmail; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
