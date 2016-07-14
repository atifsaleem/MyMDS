package my.personal.service.writer;

import my.personal.domain.MarketDataSnapshot;

/**
 * Created by atifsaleem on 13/7/16.
 *  * Reads market data from source and publishes it to the service
 */
public interface ServiceWriter {
    void runWriter();
    void writePriceToService(int second);
}
