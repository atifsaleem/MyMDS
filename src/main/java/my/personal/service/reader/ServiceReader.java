package my.personal.service.reader;

import my.personal.domain.InstrumentPrice;
import my.personal.domain.MarketDataSnapshot;

import java.util.List;

/**
 * Created by atifsaleem on 13/7/16.
 * Consumes market data from service, and determines whether any instrument price data is to be persisted to db
  */
public interface ServiceReader extends Runnable {
    void readFromService(int second) throws InterruptedException;
    boolean persistToDb(List<InstrumentPrice> instrumentPrice);
}
