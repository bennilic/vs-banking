package net.froihofer.service;

import api.TradingService;
import dto.StockDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.StockHoldingDAO;
import net.froihofer.dsfinance.ws.trading.api.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.api.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.api.TradingWebService;
import net.froihofer.entity.StockHolding;
import net.froihofer.util.TradingWebserviceProvider;

import java.util.List;

@Remote
@Stateless(name = "TradingService")
public class TradingServiceImpl implements TradingService {

    @Inject
    private TradingWebserviceProvider tradingWebserviceProvider;

    @Inject
    StockHoldingDAO stockHoldingDAO;

    @Override
    @RolesAllowed({"customer"})
    public List<StockDTO> searchStocks(String companyName) {
        System.out.println("Test");

        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();
        try {
            List<PublicStockQuote> stocks = tradingWebService.findStockQuotesByCompanyName("Apple");
            for (PublicStockQuote stock : stocks) {
                System.out.println("Storing stock: " + stock.getCompanyName() + " (" + stock.getSymbol() + ")");

                StockHolding stockHolding = new StockHolding();
                stockHolding.setSymbol(stock.getSymbol());

                stockHoldingDAO.persist(stockHolding);
            }
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
