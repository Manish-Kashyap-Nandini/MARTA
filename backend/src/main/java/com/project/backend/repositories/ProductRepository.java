package com.project.backend.repositories;

import com.project.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Check if product with same name exists for the shopkeeper
    Optional<Product> findByNameAndShopkeeperEmail(String name, String shopkeeperEmail);

    // Get all products of a shopkeeper
    List<Product> findByShopkeeperEmail(String shopkeeperEmail);
}
