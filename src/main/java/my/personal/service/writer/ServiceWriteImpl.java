package my.personal.service.writer;

import my.personal.dao.source.MarketDataDao;
import my.personal.dao.source.file.FileMarketDataDao;
import my.personal.domain.MarketDataSnapshot;
import my.personal.service.PriceProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 13/7/16.
 */
@Component
@PropertySource("classpath:common.properties")
public class ServiceWriteImpl implements ServiceWriter {
    private static final Logger LOGGER = Logger.getLogger(FileMarketDataDao.class.getName());
    private int second;

    @Value("${polling.interval}")
    private int interval;

    @Autowired
    private PriceProcessingService priceProcessingService;

    @Autowired
    private MarketDataDao marketDataDao;

    private MarketDataSnapshot marketData;


    public void run()
    {
        while(true) {
            writePriceToService(second);
            second++;
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void writePriceToService(int second) {
        try {
            marketData = marketDataDao.getMarketData(second);
            priceProcessingService.getQueue().put(marketData);
        } catch (IOException e) {
            LOGGER.severe("Cannot write to service, market data DAO is not available");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
