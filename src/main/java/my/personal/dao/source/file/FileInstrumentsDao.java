package my.personal.dao.source.file;

import my.personal.dao.source.InstrumentsDao;
import my.personal.domain.InstrumentStatic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by atifsaleem on 12/7/16.
 */
@Component
public class FileInstrumentsDao implements InstrumentsDao {
    private static final Logger LOGGER = Logger.getLogger(FileInstrumentsDao.class.getName());
    private static boolean initalized = false;
    private static final String INSTRUMENT_FILE_DATA_DELIMITER = ":";
    private static final int SYMBOL_INDEX = 0;
    private static final int WRITE_INTERVAL_INDEX = 1;


    @Value("${instruments.data.file.path}")
    private String instrumentsDataFilePath;
    private Map<String, InstrumentStatic> instrumentList;


    public FileInstrumentsDao() {
    }

    public int getInstrumentsCount() {
        if (!initalized)
        {
            this.loadInstruments();
            initalized=true;
        }
        return instrumentList.size();
    }

    public Map<String, InstrumentStatic> getInstrumentsList() {
        return instrumentList;
    }

    public InstrumentStatic getInstrumentStatic(String symbol) {
        return instrumentList.get(symbol);
    }
    public String getInstrumentsDataFilePath() {
        return instrumentsDataFilePath;
    }

    public void setInstrumentsDataFilePath(String instrumentsDataFilePath) {
        this.instrumentsDataFilePath = instrumentsDataFilePath;
    }

    public void loadInstruments() {
        this.instrumentList = new HashMap<String, InstrumentStatic>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream fileStream = classLoader.getResourceAsStream(instrumentsDataFilePath);
            Scanner scanner = new Scanner(fileStream);
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] instrumentDataArray = line.split(INSTRUMENT_FILE_DATA_DELIMITER);
                String symbol = instrumentDataArray[SYMBOL_INDEX];
                int writeInterval = Integer.parseInt(instrumentDataArray[WRITE_INTERVAL_INDEX]);
                instrumentList.put(symbol, new InstrumentStatic(symbol,writeInterval));

            }
            scanner.close();
        } catch (Exception e) {
            LOGGER.severe("Instrument static data file not found!");
            throw new RuntimeException("Instrument static data file not found!");
        }
    }

}

