package my.personal.service.reporter;

import my.personal.config.TestMarketDataServiceAppConfig;
import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.domain.MarketDataSnapshot;
import my.personal.service.PriceProcessingService;
import my.personal.service.reader.ServiceReader;
import my.personal.service.writer.ServiceWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by atifsaleem on 13/7/16.
 */
@ContextConfiguration(classes = TestMarketDataServiceAppConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceReporterImplTest {
    public static boolean instrumentStaticInitializationComplete = false;
    @Autowired
    InstrumentsDao instrumentDataDao;

    @Autowired
    PriceDataDestinationDao priceDataDestinationDao;

    @Autowired
    ServiceReporter serviceReporter;
    @Autowired
    ServiceWriter serviceWriter;
    @Autowired
    ServiceReader serviceReader;
    @Before
    public void initialize() {
        if (!instrumentStaticInitializationComplete) {
            instrumentDataDao.loadInstruments();
            instrumentStaticInitializationComplete = true;
            for (int i=0;i<30;i++)
            {
                serviceWriter.runWriter();
                serviceReader.runReader();
            }

        }
    }

    @Test
    public void testSecondHighestPerTickerReport() throws IOException {
        serviceReporter.publishSecondHighestPersistedReport();
    }
    @Test
    public void testAveragePriceOverLast10Seconds() throws IOException {
        serviceReporter.publishAveragePriceOverLastTenSecondsReport();
    }
}
