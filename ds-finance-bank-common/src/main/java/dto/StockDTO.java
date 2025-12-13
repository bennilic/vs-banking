package dto;

import java.io.Serializable;

public class StockDTO implements Serializable {
    private String symbol;

    public StockDTO (String symbol) {
        this.symbol = symbol;
    }
}
