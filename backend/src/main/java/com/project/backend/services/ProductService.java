package com.project.backend.services;

import com.project.backend.models.Product;
import com.project.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create product (no duplicate names for same shopkeeper)
    public Product createProduct(Product product, String shopkeeperEmail) {
        productRepository.findByNameAndShopkeeperEmail(product.getName(), shopkeeperEmail)
                .ifPresent(existing -> {
                    throw new RuntimeException("Product with this name already exists for your shop!");
                });

        product.setShopkeeperEmail(shopkeeperEmail); // link product to logged-in shopkeeper
        return productRepository.save(product);
    }

    // Get products by shopkeeper
    public List<Product> getProductsByShopkeeper(String shopkeeperEmail) {
        return productRepository.findByShopkeeperEmail(shopkeeperEmail);
    }
    
    
    public Product getProductById(Long id, String shopkeeperEmail) {
        return productRepository.findById(id)
                .filter(product -> product.getShopkeeperEmail().equals(shopkeeperEmail))
                .orElseThrow(() -> new RuntimeException("Product not found or not authorized"));
    }
    
    
    
    

    // Update product
    public Product updateProduct(Long id, Product updatedProduct, String shopkeeperEmail) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ensure shopkeeper owns this product
        if (!existing.getShopkeeperEmail().equals(shopkeeperEmail)) {
            throw new RuntimeException("Not allowed to update this product");
        }

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDescription(updatedProduct.getDescription());
        existing.setCategory(updatedProduct.getCategory());

        return productRepository.save(existing);
    }

    // Delete product
    public void deleteProduct(Long id, String shopkeeperEmail) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existing.getShopkeeperEmail().equals(shopkeeperEmail)) {
            throw new RuntimeException("Not allowed to delete this product");
        }

        productRepository.delete(existing);
    }
}
