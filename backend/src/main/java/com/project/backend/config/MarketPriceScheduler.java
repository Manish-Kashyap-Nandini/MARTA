package com.project.backend.config;

import com.project.backend.services.MarketPriceService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MarketPriceScheduler {

    private final MarketPriceService marketPriceService;

    public MarketPriceScheduler(MarketPriceService marketPriceService) {
        this.marketPriceService = marketPriceService;
    }

    // ✅ Run daily at 6 AM
    @Scheduled(cron = "0 0 6 * * ?")
    public void updateMarketPrices() {
        marketPriceService.fetchAndSaveMarketPrices();
    }
}
