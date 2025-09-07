package com.project.backend.controllers.dto;


public class MarketPriceDTO {
    private String commodity;
    private Double pricePerKg;
    private String unit;

    // Getters and Setters
    public String getCommodity() { return commodity; }
    public void setCommodity(String commodity) { this.commodity = commodity; }
    public Double getPricePerKg() { return pricePerKg; }
    public void setPricePerKg(Double pricePerKg) { this.pricePerKg = pricePerKg; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
