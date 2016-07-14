package my.personal.dao.source.file;

import my.personal.dao.source.InstrumentsDao;
import my.personal.dao.source.MarketDataDao;
import my.personal.exception.IllegalTimeException;
import my.personal.domain.MarketDataSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 12/7/16.
 * file based implementation of market data dao
 */
@Component
public class FileMarketDataDao implements MarketDataDao {
    private static final Logger LOGGER = Logger.getLogger(FileMarketDataDao.class.getName());
    private static final String MARKETDATA_FILE_DATA_DELIMITER = ":";
    private static final int SYMBOL_INDEX = 0;
    private static final int PRICE_INDEX = 1;

    @Autowired
    private InstrumentsDao instrumentsDao;

    @Value("${market.data.file.path}")
    private String marketDataFilePath;

    public String getMarketDataFilePath() {
        return marketDataFilePath;
    }

    public void setMarketDataFilePath(String marketDataFilePath) {
        this.marketDataFilePath = marketDataFilePath;
    }

    public FileMarketDataDao() {
    }

    public MarketDataSnapshot getMarketData(int second) throws IOException {
        MarketDataSnapshot marketDataSnapshot = new MarketDataSnapshot(second);
        if (second < 0)
            throw new IllegalTimeException("Time: " + second + " is not valid, application error", second);
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream fileStream = classLoader.getResourceAsStream(marketDataFilePath);
            Scanner scanner = new Scanner(fileStream);
            int numberOfInstruments = instrumentsDao.getInstrumentsCount();
            int numberOfLinesToSkip = second * numberOfInstruments;
            int lineCounter=0;
            while (lineCounter<numberOfLinesToSkip) {
                lineCounter++;
                String line;
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                else {
                    fileStream.close();
                    fileStream = classLoader.getResourceAsStream(marketDataFilePath);
                    scanner = new Scanner(fileStream);
                    scanner.nextLine();
                }
            }
            for (int i = 0; i < numberOfInstruments; ++i) {
                if (!scanner.hasNextLine()) {
                    fileStream.close();
                    fileStream = classLoader.getResourceAsStream(marketDataFilePath);
                    scanner = new Scanner(fileStream);
                }
                String[] instrumentDataArray = scanner.nextLine().split(MARKETDATA_FILE_DATA_DELIMITER);
                String symbol = instrumentDataArray[SYMBOL_INDEX];
                double price = Double.parseDouble(instrumentDataArray[PRICE_INDEX]);
                LOGGER.info("At sec:"+second+" reading symbol: "+symbol+" with price:"+price);
                marketDataSnapshot.addInstrumentData(instrumentsDao.getInstrumentStatic(symbol), price);
            }
            scanner.close();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new FileNotFoundException("Market data file not found!");
        }
        return marketDataSnapshot;
    }


}
