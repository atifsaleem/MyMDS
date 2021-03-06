package my.personal.service.reader;

import my.personal.dao.destination.PriceDataDestinationDao;
import my.personal.dao.source.InstrumentsDao;
import my.personal.domain.InstrumentPrice;
import my.personal.domain.InstrumentStatic;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by atifsaleem on 13/7/16.
 *
 */
@Component
@PropertySource("classpath:common.properties")
public class ServiceReaderImpl implements ServiceReader{
    private static boolean initalized = false;
    private int second;
    @Autowired
    private PriceProcessingService priceProcessingService;

    @Value("${polling.interval}")
    private int interval;

    @Autowired
     private InstrumentsDao instrumentsDao;

    @Autowired
    private PriceDataDestinationDao priceDataDestinationDao;

    private List<InstrumentStatic> instrumentStaticList;

    private MarketDataSnapshot marketData;

    public ServiceReaderImpl() {
    }
    public void setInstrumentStaticList(List<InstrumentStatic> instrumentStaticList) {
        this.instrumentStaticList = instrumentStaticList;
    }
    public void run() {
        while(true) {
            if (!initalized) {
                this.setInstrumentStaticList(new ArrayList<InstrumentStatic>(instrumentsDao.getInstrumentsList().values()));
                initalized = true;
            }
            try {
                readFromService(second);
                second++;
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
    public void readFromService(int second) throws InterruptedException{
            marketData = priceProcessingService.getQueue().take();
        Map<String, Double> priceMap = marketData.getPriceMap();
        List<InstrumentStatic> instrumentsToBePersisted = getInstrumentsToBePersistedAtThisSecond(second);
        if (instrumentsToBePersisted!=null) {
            List<InstrumentPrice> priceDataToBePersisted = getPriceData(instrumentsToBePersisted, second);
            persistToDb(priceDataToBePersisted);
        }
    }

    private List<InstrumentPrice> getPriceData(List<InstrumentStatic> instrumentsToBePersisted, int second) {
        List<InstrumentPrice> priceDataToBePersisted = new ArrayList<InstrumentPrice>();
        for (InstrumentStatic instrumentStatic : instrumentsToBePersisted)
        {
            priceDataToBePersisted.add(new InstrumentPrice(instrumentStatic.getSymbol(), marketData.getPriceMap().get(instrumentStatic.getSymbol()),second));
        }
        return priceDataToBePersisted;
    }

    private List<InstrumentStatic> getInstrumentsToBePersistedAtThisSecond(int second) {
        List<InstrumentStatic> instrumentsToBePersisted = new ArrayList<InstrumentStatic>();
        for (InstrumentStatic instrumentStatic : instrumentStaticList)
        {
            if (second==0 || second%instrumentStatic.getWriteInterval() == 0)
                instrumentsToBePersisted.add(instrumentStatic);
        }
        return instrumentsToBePersisted;
    }

    public boolean persistToDb(List<InstrumentPrice> instrumentPrices) {
        return priceDataDestinationDao.persistPriceData(instrumentPrices);
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
