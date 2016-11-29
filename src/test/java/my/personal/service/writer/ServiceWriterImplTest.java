package my.personal.service.writer;

import my.personal.config.TestMarketDataServiceAppConfig;
import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.domain.MarketDataSnapshot;
import my.personal.service.PriceProcessingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Created by atifsaleem on 13/7/16.
 */
@ContextConfiguration(classes = TestMarketDataServiceAppConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceWriterImplTest {
    public static boolean instrumentStaticInitializationComplete = false;
    @Autowired
    InstrumentsDao instrumentDataDao;

    @Autowired
    MarketDataDao marketDataDao;

    @Autowired
    ServiceWriter serviceWriter;

    @Autowired
    PriceProcessingService priceProcessingService;

    @Before
    public void initialize() {
        if (!instrumentStaticInitializationComplete) {
            instrumentDataDao.loadInstruments();
            instrumentStaticInitializationComplete = true;
        }
    }

    @Test
    public void testWritingToServiceAtTheBeginningOfTime() throws IOException, InterruptedException {
        int second = 0;
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.submit(serviceWriter);
        MarketDataSnapshot marketData = priceProcessingService.getQueue().take();
        HashMap<String, Double> priceMap = marketData.getPriceMap();
        assertEquals(100.0, priceMap.get("BT.L"), 0.01);
        assertEquals(85.0, priceMap.get("VOD.L"), 0.01);
        assertEquals(86.67, priceMap.get("GOOG"), 0.01);
        assertEquals(1450.4, priceMap.get("BP.L"), 0.01);

    }

}
