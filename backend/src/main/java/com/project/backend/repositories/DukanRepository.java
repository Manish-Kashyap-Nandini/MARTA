package com.project.backend.repositories;


import com.project.backend.models.Dukan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DukanRepository extends JpaRepository<Dukan, Long> {
    List<Dukan> findByOwnerId(Long ownerId);
}

