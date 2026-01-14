package api;

import dto.StockDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TradingService {
    List<StockDTO> searchStocks(String companyName);

    BigDecimal buyStock(String symbol, int quantity);

    BigDecimal sellStock(String symbol, int quantity);
}
