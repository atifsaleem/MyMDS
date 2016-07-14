package my.personal.service.writer;

import my.personal.dao.source.MarketDataDao;
import my.personal.dao.source.file.FileMarketDataDao;
import my.personal.domain.MarketDataSnapshot;
import my.personal.service.PriceProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 13/7/16.
 */
@Component
public class ServiceWriteImpl implements ServiceWriter {
    private static final Logger LOGGER = Logger.getLogger(FileMarketDataDao.class.getName());
    private int second;
    @Autowired
    private PriceProcessingService priceProcessingService;

    @Autowired
    private MarketDataDao marketDataDao;

    private MarketDataSnapshot marketData;

    public void runWriter()
    {
        writePriceToService(second);
        second++;
    }

    public void writePriceToService(int second) {
        try {
            marketData = marketDataDao.getMarketData(second);
            priceProcessingService.setPrices(marketData);
        } catch (IOException e) {
            LOGGER.severe("Cannot write to service, market data DAO is not available");
        }
    }
}
