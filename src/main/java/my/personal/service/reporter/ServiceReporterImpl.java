package my.personal.service.reporter;


import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.destination.jdbc.jdbcPriceDataDestinationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 14/7/16.
 */
@Component
public class ServiceReporterImpl implements ServiceReporter {
    private static final Logger LOGGER = Logger.getLogger(ServiceReporterImpl.class.getName());

    @Autowired
    private PriceDataDestinationDao priceDataDestinationDao;

    public void run() {
        this.publishAveragePriceOverLastTenSecondsReport();
        this.publishSecondHighestPersistedReport();
    }
    public void publishSecondHighestPersistedReport() {
        LOGGER.info("Publishing second highest price per ticker");
        List<Map<String,Object>> rows = priceDataDestinationDao.getSecondHighestPricePerTicker();
        for (Map<String,Object> row : rows)
        {
            LOGGER.info((String) row.get("SYMBOL") + ":" + (BigDecimal) row.get(""));
        }
    }

    public void publishAveragePriceOverLastTenSecondsReport() {
        LOGGER.info("Publishing average price per ticker over last ten seconds");
        List<Map<String,Object>> rows = priceDataDestinationDao.getAveragePriceOverLastTenSeconds();
        for (Map<String,Object> row : rows)
        {
            LOGGER.info((String) row.get("SYMBOL") + ":" + (BigDecimal) row.get(""));
        }

    }
}
