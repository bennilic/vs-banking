package api;

import dto.StockDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TradingService {
    List<StockDTO> searchStocks(String companyName);

    BigDecimal buyStock(String symbol, int quantity, Long customerId);

    BigDecimal sellStock(String symbol, int quantity, Long customerId);
}
