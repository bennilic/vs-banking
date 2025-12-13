package service;

import api.TradingService;
import dto.StockDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dsfinance.ws.trading.api.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.api.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.api.TradingWebService;
import net.froihofer.util.TradingWebserviceProvider;

import java.util.List;

@Remote
@Stateless(name = "TradingService")
public class TradingServiceImpl implements TradingService {

    @Inject
    private TradingWebserviceProvider tradingWebserviceProvider;

    @Override
    @RolesAllowed({"customer"})
    public List<StockDTO> searchStocks(String companyName) {
        System.out.println("Test");

        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();
        try {
            List<PublicStockQuote> stocks = tradingWebService.findStockQuotesByCompanyName("Apple");
            for (PublicStockQuote stock : stocks) {
                System.out.println("Found stock: " + stock.getCompanyName() + " (" + stock.getSymbol() + ")");
            }
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
