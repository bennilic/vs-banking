package dto;

import java.io.Serializable;

public class StockDTO implements Serializable {
    private String symbol;

    public StockDTO () {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
