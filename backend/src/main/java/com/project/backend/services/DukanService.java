package com.project.backend.services;

import com.project.backend.models.Dukan;
import com.project.backend.repositories.DukanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DukanService {

    private final DukanRepository dukanRepository;

    public DukanService(DukanRepository dukanRepository) {
        this.dukanRepository = dukanRepository;
    }

    public Dukan createDukan(Dukan dukan) {
        return dukanRepository.save(dukan);
    }

    public Dukan updateDukan(Long id, Dukan dukanDetails) {
        Dukan dukan = dukanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dukan not found"));

        dukan.setName(dukanDetails.getName());
        dukan.setAddress(dukanDetails.getAddress());
        dukan.setContactNumber(dukanDetails.getContactNumber());
        dukan.setDescription(dukanDetails.getDescription());
        dukan.setOwner(dukanDetails.getOwner());

        return dukanRepository.save(dukan);
    }

    public void deleteDukan(Long id) {
        dukanRepository.deleteById(id);
    }

    public Optional<Dukan> getDukanById(Long id) {
        return dukanRepository.findById(id);
    }

    public List<Dukan> getAllDukan() {
        return dukanRepository.findAll();
    }

    public List<Dukan> getDukanByOwner(Long ownerId) {
        return dukanRepository.findByOwnerId(ownerId);
    }
}




