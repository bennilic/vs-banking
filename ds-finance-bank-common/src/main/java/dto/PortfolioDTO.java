package dto;

import java.io.Serializable;
import java.util.List;

public class PortfolioDTO implements Serializable {

    private List<StockDTO> stocks;

    public PortfolioDTO() {
    }

    public List<StockDTO> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDTO> stocks) {
        this.stocks = stocks;
    }
}
