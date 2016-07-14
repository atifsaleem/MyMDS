package my.personal.dao.source.file;

import my.personal.config.TestMarketDataServiceAppConfig;
import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.exception.IllegalTimeException;
import my.personal.domain.MarketDataSnapshot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by atifsaleem on 12/7/16.
 */
@ContextConfiguration(classes = TestMarketDataServiceAppConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class FileMarketDataDaoTest {
    public static boolean instrumentStaticInitializationComplete = false;
    @Autowired
    InstrumentsDao instrumentDataDao;

    @Autowired
    MarketDataDao dataDao;

    @Before
    public void initialize() {
        if (!instrumentStaticInitializationComplete) {
            instrumentDataDao.loadInstruments();
            instrumentStaticInitializationComplete = true;
        }
    }

    @Test
    public void testGetMarketDataAtTheBeginningOfTime() throws IOException {
        int seconds = 0;
        MarketDataSnapshot marketData = dataDao.getMarketData(seconds);
        HashMap<String, Double> priceMap = marketData.getPriceMap();
        assertEquals(100.0, priceMap.get("BT.L"), 0.01);
        assertEquals(85.0, priceMap.get("VOD.L"), 0.01);
        assertEquals(86.67, priceMap.get("GOOG"), 0.01);
        assertEquals(1450.4, priceMap.get("BP.L"), 0.01);
    }


    @Test(expected = IllegalTimeException.class)
    public void testGetMarketDataAtNegativeTime() throws IOException {
        int seconds = -1;
        dataDao.getMarketData(seconds);
    }

    @Test
    public void testGetMarketDataAtTEqualsOne() throws IOException {
        int seconds = 1;
        MarketDataSnapshot marketData = dataDao.getMarketData(seconds);
        HashMap<String, Double> priceMap = marketData.getPriceMap();
        assertEquals(98.0, priceMap.get("BT.L"), 0.01);
        assertEquals(87.0, priceMap.get("VOD.L"), 0.01);
        assertEquals(84.67, priceMap.get("GOOG"), 0.01);
        assertEquals(1460.4, priceMap.get("BP.L"), 0.01);
    }

    @Test
    public void testGetMarketDataAtTEqualsTen() throws IOException {
        int seconds = 10;
        MarketDataSnapshot marketData = dataDao.getMarketData(seconds);
        HashMap<String, Double> priceMap = marketData.getPriceMap();
        assertEquals(100.0, priceMap.get("BT.L"), 0.01);
        assertEquals(85.0, priceMap.get("VOD.L"), 0.01);
        assertEquals(86.67, priceMap.get("GOOG"), 0.01);
        assertEquals(1450.4, priceMap.get("BP.L"), 0.01);
    }

    @Test
    public void testGetMarketDataAtTEqualsEleven() throws IOException {
        int seconds = 11;
        MarketDataSnapshot marketData = dataDao.getMarketData(seconds);
        HashMap<String, Double> priceMap = marketData.getPriceMap();
        assertEquals(98.0, priceMap.get("BT.L"), 0.01);
        assertEquals(87.0, priceMap.get("VOD.L"), 0.01);
        assertEquals(84.67, priceMap.get("GOOG"), 0.01);
        assertEquals(1460.4, priceMap.get("BP.L"), 0.01);
    }

    @Test
    public void testGetMarketDataAtTEqualsTwentyNine() throws IOException {
        int seconds = 29;
        MarketDataSnapshot marketData = dataDao.getMarketData(seconds);
        HashMap<String, Double> priceMap = marketData.getPriceMap();
        assertEquals(97.0, priceMap.get("BT.L"), 0.01);
        assertEquals(95.0, priceMap.get("VOD.L"), 0.01);
        assertEquals(85.6, priceMap.get("GOOG"), 0.01);
        assertEquals(1450.1, priceMap.get("BP.L"), 0.01);
    }


}
