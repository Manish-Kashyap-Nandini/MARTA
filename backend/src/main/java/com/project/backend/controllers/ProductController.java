package com.project.backend.controllers;

import com.project.backend.models.Product;
import com.project.backend.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/shop/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ➕ Create product (without image)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, Authentication auth) {
        String shopkeeperEmail = auth.getName(); // logged-in shopkeeper
        return ResponseEntity.ok(productService.createProduct(product, shopkeeperEmail));
    }

    // ➕ Create product (with image upload)
    @PostMapping("/upload")
    public ResponseEntity<Product> uploadProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            Authentication auth) throws IOException {

        String shopkeeperEmail = auth.getName();

        // Save file into "uploads/" folder
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent()); // ensure uploads/ exists
        Files.write(path, file.getBytes());

        // URL to serve this image
        String imageUrl = "http://localhost:8080/uploads/" + fileName;

        Product product = new Product(name, price, description, category, shopkeeperEmail);
        product.setImageUrl(imageUrl);

        return ResponseEntity.ok(productService.createProduct(product, shopkeeperEmail));
    }

    // 📜 Get all products of this shopkeeper
    @GetMapping
    public ResponseEntity<List<Product>> getMyProducts(Authentication auth) {
        String shopkeeperEmail = auth.getName();
        return ResponseEntity.ok(productService.getProductsByShopkeeper(shopkeeperEmail));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        Product product = productService.getProductById(id, email);
        return ResponseEntity.ok(product);
    }
    
    
    

    // ✏️ Update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product,
            Authentication auth) {
        String shopkeeperEmail = auth.getName();
        return ResponseEntity.ok(productService.updateProduct(id, product, shopkeeperEmail));
    }

    // ❌ Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Authentication auth) {
        String shopkeeperEmail = auth.getName();
        productService.deleteProduct(id, shopkeeperEmail);
        return ResponseEntity.ok().build();
    }
}
