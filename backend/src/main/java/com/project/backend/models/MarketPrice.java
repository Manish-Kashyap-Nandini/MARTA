package com.project.backend.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commodity;   // e.g., "Wheat"
    private Double pricePerKg;  // e.g., 25.5
    private String unit;        // e.g., "kg"
    private LocalDate date;     // Date of price

    public MarketPrice() {}

    public MarketPrice(String commodity, Double pricePerKg, String unit, LocalDate date) {
        this.commodity = commodity;
        this.pricePerKg = pricePerKg;
        this.unit = unit;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getCommodity() { return commodity; }
    public void setCommodity(String commodity) { this.commodity = commodity; }
    public Double getPricePerKg() { return pricePerKg; }
    public void setPricePerKg(Double pricePerKg) { this.pricePerKg = pricePerKg; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
