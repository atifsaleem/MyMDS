package my.personal.service;

import my.personal.domain.MarketDataSnapshot;
import org.springframework.stereotype.Service;

/**
 * Created by atifsaleem on 13/7/16.
 */
@Service
public class PriceProcessingServiceImpl implements PriceProcessingService {

    private MarketDataSnapshot marketDataSnapshot;

    public void setPrices(MarketDataSnapshot snapshot) {
        this.marketDataSnapshot = snapshot;
    }

    public MarketDataSnapshot getPrices() {
        return marketDataSnapshot;
    }

}
