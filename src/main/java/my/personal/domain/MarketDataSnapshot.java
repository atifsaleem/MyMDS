package my.personal.domain;

import java.util.HashMap;

/**
 * Created by atifsaleem on 12/7/16.
 */
public class MarketDataSnapshot {
    private int second;
    private HashMap<String, Double> priceMap;

    public MarketDataSnapshot() {

    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public HashMap<String, Double> getPriceMap() {
        return priceMap;
    }

    public void setPriceMap(HashMap<String, Double> priceMap) {
        this.priceMap = priceMap;
    }

    public MarketDataSnapshot(int second, HashMap<String, Double> priceMap) {
        this.second = second;
        this.priceMap = priceMap;
    }
    public MarketDataSnapshot(int second){
        this.setSecond(second);
        this.priceMap = new HashMap<String, Double>();
    }
    public void addInstrumentData(InstrumentStatic instrumentStatic, double price) {
        this.priceMap.put(instrumentStatic.getSymbol(), price);
    }

}