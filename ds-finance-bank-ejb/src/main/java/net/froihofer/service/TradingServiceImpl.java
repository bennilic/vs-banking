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

import java.util.ArrayList;
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

        // Request to stock exchange web service
        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();
        try {
            // Gets stock in the default structure from the stock exchange web service
            List<PublicStockQuote> stocks = tradingWebService.findStockQuotesByCompanyName("Apple");

            // Creates an empty stock list, that is later send back to the client
            List<StockDTO> stockDTOList = new ArrayList<>();

            for (PublicStockQuote stock : stocks) {
                System.out.println("Storing stock: " + stock.getCompanyName() + " (" + stock.getSymbol() + ")");

                // Mapping from Froihofers stock structure to our own stock structure for the database.
                StockHolding stockHolding = new StockHolding();
                stockHolding.setSymbol(stock.getSymbol());

                // Mapping from Froihofers stock structure to our own stock structure for the client.
                StockDTO stockDTO = new StockDTO();
                stockDTO.setSymbol(stock.getSymbol());
                stockDTOList.add(stockDTO);

                // Save the stocks in the database
                stockHoldingDAO.persist(stockHolding);

                // Return the list of stocks to the client
                return stockDTOList;
            }
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
