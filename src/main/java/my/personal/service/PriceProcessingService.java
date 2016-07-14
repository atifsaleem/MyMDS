package my.personal.service;

import my.personal.domain.MarketDataSnapshot;

/**
 * Created by atifsaleem on 13/7/16.
 */
public interface PriceProcessingService {
    void setPrices(MarketDataSnapshot snapshot);
    MarketDataSnapshot getPrices();
}
