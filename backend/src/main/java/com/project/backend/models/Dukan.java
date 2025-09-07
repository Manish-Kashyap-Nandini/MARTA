package com.project.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "dukan")
public class Dukan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String contactNumber;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner; // Only a SHOPKEEPER can own

    public Dukan() {}

    public Dukan(String name, String address, String contactNumber, String description, User owner) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.description = description;
        this.owner = owner;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
