package my.personal.service;

import my.personal.domain.MarketDataSnapshot;

/**
 * Created by atifsaleem on 13/7/16.
 * Core price processing service. Writer writes to this service, reader reads from service
 * Application is single threaded, so thread safety is not under consideration
 */
public interface PriceProcessingService {
    void setPrices(MarketDataSnapshot snapshot);
    MarketDataSnapshot getPrices();
}
