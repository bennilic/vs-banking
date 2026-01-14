package net.froihofer.service;

import api.TradingService;
import dto.StockDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.BankDAO;
import net.froihofer.dao.PortfolioDAO;
import net.froihofer.dsfinance.ws.trading.api.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.api.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.api.TradingWebService;
import net.froihofer.entity.Bank;
import net.froihofer.entity.Customer;
import net.froihofer.entity.Portfolio;
import net.froihofer.entity.StockHolding;
import net.froihofer.util.SessionUtils;
import net.froihofer.util.TradingWebserviceProvider;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Remote
@Stateless(name = "TradingService")
public class TradingServiceImpl implements TradingService {

    @Inject
    private TradingWebserviceProvider tradingWebserviceProvider;

    @Inject
    PortfolioDAO portfolioDAO;

    @Inject
    BankDAO bankDAO;

    @Inject
    SessionUtils sessionUtils;

    @Override
    @RolesAllowed({"customer", "employee"})
    public List<StockDTO> searchStocks(String companyName) {
        // TODO Remove Storing stocks in the database, only return to client
        // Request to stock exchange web service
        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();
        try {
            // Gets stock in the default structure from the stock exchange web service
            List<PublicStockQuote> stocks = tradingWebService.findStockQuotesByCompanyName(companyName);

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
                portfolioDAO.persist(stockHolding);

                // Return the list of stocks to the client
                return stockDTOList;
            }
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    @RolesAllowed("customer")
    public BigDecimal buyStock(String symbol, int quantity) {
        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();

        try {
            BigDecimal purchaseValue = tradingWebService.buy(symbol, quantity);
            BigDecimal totalPurchaseValue = purchaseValue.multiply(BigDecimal.valueOf(quantity));

            Customer customer = sessionUtils.getLoggedInCustomer();

            Portfolio portfolio = customer.getPortfolio();

            StockHolding stockHolding = new StockHolding();
            stockHolding.setSymbol(symbol);
            stockHolding.setQuantity(quantity);
            stockHolding.setPurchasePrice(purchaseValue);

            portfolio.addStockHolding(stockHolding);
            portfolioDAO.persist(portfolio);

            Bank bank = bankDAO.findById(1);
            BigDecimal investibleVolume = bank.getInvestableVolume();

            bank.setInvestableVolume(investibleVolume.subtract(totalPurchaseValue));

            bankDAO.persist(bank);

            return purchaseValue;
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @RolesAllowed("customer")
    public BigDecimal sellStock(String symbol, int quantity) {
        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();

        try {
            Customer customer = sessionUtils.getLoggedInCustomer();

            Portfolio portfolio = customer.getPortfolio();
            List<StockHolding> stockHoldings = portfolio.getStockHoldings();

            int totalStockQuantity = 0;
            for (StockHolding stockHolding : stockHoldings) {
                if (stockHolding.getSymbol().equals(symbol)) {
                    totalStockQuantity += stockHolding.getQuantity();
                }
            }

            if (totalStockQuantity < quantity) {
                throw new RuntimeException("Not enough stocks to sell.");
            }

            int originalQuantity = quantity;
            for (StockHolding stockHolding : stockHoldings){
                int stockQuantity = stockHolding.getQuantity();

                if (stockQuantity <= quantity){
                    stockHolding.setQuantity(0);
                    quantity = quantity - stockQuantity;
                } else {
                    stockHolding.setQuantity(stockQuantity - quantity);
                    quantity = 0;
                }
            }

            BigDecimal sellingValue = tradingWebService.sell(symbol, originalQuantity);
            BigDecimal totalSellingValue = sellingValue.multiply(BigDecimal.valueOf(originalQuantity));

            Bank bank = bankDAO.findById(1);
            BigDecimal investibleVolume = bank.getInvestableVolume();

            bank.setInvestableVolume(investibleVolume.add(totalSellingValue));

            bankDAO.persist(bank);

            return sellingValue;
        } catch (TradingWSException_Exception e) {
            throw new RuntimeException(e);
        }
    }
}
