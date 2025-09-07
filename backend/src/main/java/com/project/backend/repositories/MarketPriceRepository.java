package com.project.backend.repositories;


import com.project.backend.models.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {
    Optional<MarketPrice> findByCommodityAndDate(String commodity, LocalDate date);
}
