package com.project.backend.services;




import com.project.backend.controllers.dto.MarketPriceDTO;
import com.project.backend.models.MarketPrice;
import com.project.backend.repositories.MarketPriceRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class MarketPriceService {

    private final MarketPriceRepository repository;
    private final RestTemplate restTemplate;

    public MarketPriceService(MarketPriceRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // ✅ Fetch and Save from External API
    public void fetchAndSaveMarketPrices() {
        String apiUrl = "https://api.example.gov/mandi-rates"; // Replace with real govt. API

        ResponseEntity<List<MarketPriceDTO>> response =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<MarketPriceDTO>>() {});

        if (response.getBody() != null) {
            for (MarketPriceDTO dto : response.getBody()) {
                // Prevent duplicate entry for same commodity & date
                repository.findByCommodityAndDate(dto.getCommodity(), LocalDate.now())
                        .orElseGet(() -> repository.save(new MarketPrice(
                                dto.getCommodity(),
                                dto.getPricePerKg(),
                                dto.getUnit(),
                                LocalDate.now()
                        )));
            }
        }
    }

    public List<MarketPrice> getAllMarketPrices() {
        return repository.findAll();
    }
}
