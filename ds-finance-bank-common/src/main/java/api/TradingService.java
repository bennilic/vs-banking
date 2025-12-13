package api;

import dto.StockDTO;

import java.util.List;

public interface TradingService {
    List<StockDTO> searchStocks(String companyName);
}
