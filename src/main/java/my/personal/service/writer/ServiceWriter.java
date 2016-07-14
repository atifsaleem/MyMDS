package my.personal.service.writer;

import my.personal.domain.MarketDataSnapshot;

/**
 * Created by atifsaleem on 13/7/16.
 */
public interface ServiceWriter {
    void runWriter();
    void writePriceToService(int second);
}
