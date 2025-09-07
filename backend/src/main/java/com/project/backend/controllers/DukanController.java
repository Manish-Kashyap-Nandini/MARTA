package com.project.backend.controllers;

import com.project.backend.models.Dukan;
import com.project.backend.models.User;
import com.project.backend.services.DukanService;
import com.project.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/dukan")
public class DukanController {

    private final DukanService dukanService;
    private final UserService userService;  // ✅ Added

    public DukanController(DukanService dukanService, UserService userService) {
        this.dukanService = dukanService;
        this.userService = userService;
    }

    // ✅ Create Dukan (Only SHOPKEEPER)
    @PostMapping
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public ResponseEntity<Dukan> createDukan(@RequestBody Dukan dukan) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User owner = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        dukan.setOwner(owner);
        return ResponseEntity.ok(dukanService.createDukan(dukan));
    }

    // ✅ Update Dukan
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public ResponseEntity<Dukan> updateDukan(@PathVariable Long id, @RequestBody Dukan dukanDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User owner = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        dukanDetails.setOwner(owner);
        return ResponseEntity.ok(dukanService.updateDukan(id, dukanDetails));
    }

    // ✅ Get all Dukan (any logged in user can view)
    @GetMapping
    public ResponseEntity<List<Dukan>> getAllDukan() {
        return ResponseEntity.ok(dukanService.getAllDukan());
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Dukan> getDukanById(@PathVariable Long id) {
        return dukanService.getDukanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete (Only SHOPKEEPER)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public ResponseEntity<Void> deleteDukan(@PathVariable Long id) {
        dukanService.deleteDukan(id);
        return ResponseEntity.noContent().build();
    }
}
