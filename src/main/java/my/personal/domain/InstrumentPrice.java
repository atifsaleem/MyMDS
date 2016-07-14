package my.personal.domain;

/**
 * Created by atifsaleem on 13/7/16.
 * domain object for persisting price data for an instrument into db
 */
public class InstrumentPrice {
    private String symbol;
    private double price;
    private int time;

    public InstrumentPrice(String symbol, double price, int time) {
        this.symbol = symbol;
        this.price = price;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
