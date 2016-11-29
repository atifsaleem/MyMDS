package my.personal.dao.destination;

import my.personal.domain.InstrumentPrice;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Map;

/**
 * Created by atifsaleem on 13/7/16.
 * DAO for persisting price data. Also used by the reporter service to generate reports
 */
public interface PriceDataDestinationDao {
    boolean persistPriceData(List<InstrumentPrice> prices);
    int executeQueryForCount(String sql);
    List getSecondHighestPricePerTicker();
    List getAveragePriceOverLastTenSeconds();
}
