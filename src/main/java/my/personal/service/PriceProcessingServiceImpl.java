package my.personal.service;

import my.personal.domain.MarketDataSnapshot;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by atifsaleem on 13/7/16.
 */
@Service
public class PriceProcessingServiceImpl implements PriceProcessingService {

    public SynchronousQueue<MarketDataSnapshot> marketDataSnapshot;

    @PostConstruct
    public void init(){
        marketDataSnapshot = new SynchronousQueue<MarketDataSnapshot>();
    }
    public void setPrices(MarketDataSnapshot snapshot) throws InterruptedException {

        this.marketDataSnapshot.put(snapshot);
    }

    public MarketDataSnapshot getPrices() throws InterruptedException {
        return this.marketDataSnapshot.take();
    }

    public SynchronousQueue<MarketDataSnapshot> getQueue() {
        return marketDataSnapshot;
    }

}
