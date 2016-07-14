package my.personal.dao.source;

import my.personal.domain.InstrumentStatic;

import java.util.List;
import java.util.Map;

/**
 * Created by atifsaleem on 12/7/16.
 * dao for instrument static. Instrument static data contains the ticker symbol and persistence interval for the symbol (e.g: a tuple would be GOOG-3, since the GOOG symbol is to be persisted every 3 secs)
 */
public interface InstrumentsDao {
    int getInstrumentsCount();
    Map<String, InstrumentStatic> getInstrumentsList();
    InstrumentStatic getInstrumentStatic(String symbol);
    void loadInstruments();
}
