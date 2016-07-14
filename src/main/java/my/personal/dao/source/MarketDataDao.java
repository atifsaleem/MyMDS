package my.personal.dao.source;

import my.personal.domain.MarketDataSnapshot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by atifsaleem on 12/7/16.
 */
public interface MarketDataDao {
    MarketDataSnapshot getMarketData(int second) throws IOException;
}
