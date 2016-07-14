package my.personal.service.reader;

import my.personal.domain.InstrumentPrice;
import my.personal.domain.MarketDataSnapshot;

import java.util.List;

/**
 * Created by atifsaleem on 13/7/16.
 */
public interface ServiceReader {
    void runReader();
    void readFromService(int second);
    boolean persistToDb(List<InstrumentPrice> instrumentPrice);
}
