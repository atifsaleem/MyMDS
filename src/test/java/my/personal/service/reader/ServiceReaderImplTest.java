package my.personal.service.reader;

import my.personal.config.TestMarketDataServiceAppConfig;
import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.domain.InstrumentStatic;
import my.personal.domain.MarketDataSnapshot;
import my.personal.service.PriceProcessingService;
import my.personal.service.writer.ServiceWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by atifsaleem on 13/7/16.
 */
@ContextConfiguration(classes = TestMarketDataServiceAppConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceReaderImplTest {
    public static boolean instrumentStaticInitializationComplete = false;
    @Autowired
    InstrumentsDao instrumentDataDao;
    @Autowired
    MarketDataDao marketDataDao;
    @Autowired
    ServiceWriter serviceWriter;
    @Autowired
    ServiceReader serviceReader;
    @Autowired
    PriceProcessingService priceProcessingService;
    @Autowired
    PriceDataDestinationDao priceDataDestinationDao;
    @Before
    public void initialize() {
        if (!instrumentStaticInitializationComplete) {
            instrumentDataDao.loadInstruments();
            instrumentStaticInitializationComplete = true;
            ServiceReaderImpl castedServiceReader = (ServiceReaderImpl) serviceReader;
            castedServiceReader.setInstrumentStaticList(new ArrayList<InstrumentStatic>(instrumentDataDao.getInstrumentsList().values()));
        }
    }

    @Test
    public void testReaderFromServiceAtTheBeginningOfTime() throws IOException {
        int second = 0;
        serviceWriter.writePriceToService(second);
        MarketDataSnapshot marketData = priceProcessingService.getPrices();
        serviceReader.readFromService(second);
    }

    @Test
    public void testReaderFromServiceAtThreeSeconds() throws IOException {
        int second = 3;
        serviceWriter.writePriceToService(second);
        MarketDataSnapshot marketData = priceProcessingService.getPrices();
        serviceReader.readFromService(second);
    }
    @Test
    public void testReaderFromServiceAtFiveSeconds() throws IOException {
        int second = 5;
        serviceWriter.writePriceToService(second);
        MarketDataSnapshot marketData = priceProcessingService.getPrices();
        serviceReader.readFromService(second);
    }
    @Test
    public void testReaderFromServiceAtSixSeconds() throws IOException {
        int second = 6;
        serviceWriter.writePriceToService(second);
        MarketDataSnapshot marketData = priceProcessingService.getPrices();
        serviceReader.readFromService(second);
    }

    @Test
    public void testReaderFromServiceAtTenSeconds() throws IOException {
        int second = 10;
        serviceWriter.writePriceToService(second);
        MarketDataSnapshot marketData = priceProcessingService.getPrices();
        serviceReader.readFromService(second);
    }


}
