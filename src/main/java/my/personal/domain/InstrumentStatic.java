package my.personal.domain;

/**
 * Created by atifsaleem on 12/7/16.
 */
public class InstrumentStatic {
    private String symbol;
    private int writeInterval;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public int getWriteInterval() {
        return writeInterval;
    }

    public void setWriteInterval(int writeInterval) {
        this.writeInterval = writeInterval;
    }

    public InstrumentStatic(){

    }
    public InstrumentStatic(String symbol, int writeInterval){

        this.symbol = symbol;
        this.writeInterval = writeInterval;
    }

}
