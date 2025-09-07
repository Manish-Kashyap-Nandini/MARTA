package com.project.backend.repositories;

import com.project.backend.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Find inventory row by product id
    Optional<Inventory> findByProductId(Long productId);

    // Check if inventory exists for a product
    boolean existsByProductId(Long productId);

    // ✅ Delete by productId (Spring Data generates real DELETE SQL)
    //void deleteByProductId(Long productId);

    // ✅ Explicit delete by inventory id
    @Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.id = :inventoryId")
    void deleteByInventoryId(@Param("inventoryId") Long inventoryId);

    // ✅ Fetch all inventory belonging to a shopkeeper
    List<Inventory> findByProduct_ShopkeeperEmail(String shopkeeperEmail);
}
