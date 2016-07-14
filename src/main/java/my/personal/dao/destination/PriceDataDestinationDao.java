package my.personal.dao.destination;

import my.personal.domain.InstrumentPrice;

import java.util.List;
import java.util.Map;

/**
 * Created by atifsaleem on 13/7/16.
 * DAO for persisting price data. Also used by the reporter service to generate reports
 */
public interface PriceDataDestinationDao {
    boolean persistPriceData(List<InstrumentPrice> prices);
    boolean executeQuery(String sql);
    List getSecondHighestPricePerTicker();
    List getAveragePriceOverLastTenSeconds();
}
