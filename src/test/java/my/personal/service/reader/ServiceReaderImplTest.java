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
import java.util.HashMap;
import java.util.concurrent.*;

import static com.sun.javaws.JnlpxArgs.verify;
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
    public void testReaderFromServiceAtTheBeginningOfTime() throws IOException, InterruptedException {
        int second = 0;
        final MarketDataSnapshot marketDataSnapshot = new MarketDataSnapshot();
        HashMap<String, Double> priceMap = new HashMap<String, Double>();
        priceMap.put("BT.L",100.0);
        priceMap.put("VOD.L",200.0);
        priceMap.put("BP.L",200.0);
        priceMap.put("GOOG",200.0);
        marketDataSnapshot.setPriceMap(priceMap);
        ExecutorService ex = Executors.newFixedThreadPool(2);
        final Future handler2 = ex.submit(new Runnable(){
            public void run(){
                try {
                    priceProcessingService.getQueue().put(marketDataSnapshot);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        final Future handler1 = ex.submit(serviceReader);
        try {
            System.out.println("Started..");
            handler1.get(1, TimeUnit.SECONDS);
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            handler1.cancel(true);
            ex.shutdownNow();
            System.out.println("Terminated!");
            int rows = priceDataDestinationDao.executeQueryForCount("select count(*) from priceData");
            assertEquals(4,rows);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
