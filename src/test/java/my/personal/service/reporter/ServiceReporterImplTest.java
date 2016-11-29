package my.personal.service.reporter;

import my.personal.config.TestMarketDataServiceAppConfig;
import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.domain.InstrumentPrice;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        for (int i = 20; i<=30 ; i++)
        {   List<InstrumentPrice> instrumentPriceList = new ArrayList<InstrumentPrice>();
            instrumentPriceList.add(new InstrumentPrice("BT.L",100.0+i,i));
            instrumentPriceList.add(new InstrumentPrice("BP.L",100+i,i));
            instrumentPriceList.add(new InstrumentPrice("VOD.L",100+i,i));
            instrumentPriceList.add(new InstrumentPrice("GOOG",100+i,i));
            priceDataDestinationDao.persistPriceData(instrumentPriceList);
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
