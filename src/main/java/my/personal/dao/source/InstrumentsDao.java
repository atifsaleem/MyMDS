package my.personal.dao.source;

import my.personal.domain.InstrumentStatic;

import java.util.List;
import java.util.Map;

/**
 * Created by atifsaleem on 12/7/16.
 */
public interface InstrumentsDao {
    int getInstrumentsCount();
    Map<String, InstrumentStatic> getInstrumentsList();
    InstrumentStatic getInstrumentStatic(String symbol);
    void loadInstruments();
}
