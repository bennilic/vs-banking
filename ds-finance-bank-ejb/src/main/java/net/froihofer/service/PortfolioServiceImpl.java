package net.froihofer.service;

import api.PortfolioService;
import dto.PortfolioDTO;
import dto.StockDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.UserDAO;
import net.froihofer.dsfinance.ws.trading.api.PublicStockQuote;
import net.froihofer.dsfinance.ws.trading.api.TradingWebService;
import net.froihofer.dsfinance.ws.trading.api.TradingWebServiceService;
import net.froihofer.entity.Customer;
import net.froihofer.entity.Portfolio;
import net.froihofer.entity.StockHolding;
import net.froihofer.util.SessionUtils;
import net.froihofer.util.TradingWebserviceProvider;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

@Remote
@Stateless(name = "PortfolioService")
public class PortfolioServiceImpl implements PortfolioService {
    @Inject
    SessionUtils sessionUtils;

    @Inject
    UserDAO userDAO;

    @Inject
    private TradingWebserviceProvider tradingWebserviceProvider;

    @Override
    @RolesAllowed({"customer", "employee"})
    public PortfolioDTO listPortfolio(Long customerId) {
        TradingWebService tradingWebService = tradingWebserviceProvider.getTradingWebService();

        Customer customer;
        if (sessionUtils.isEmployee()) {
            if (customerId == null) {
                throw new RuntimeException("Customer ID is required for selling stocks.");
            } else {
                customer = userDAO.findById(customerId);
            }
        } else {
            customer = sessionUtils.getLoggedInCustomer();
        }

        Portfolio portfolio = customer.getPortfolio();

        PortfolioDTO portfolioDTO = new PortfolioDTO();
        List<StockDTO> stockDTOList = new ArrayList<>();
        for (StockHolding stockHolding : portfolio.getStockHoldings()) {

            StockDTO stockDTO = new StockDTO();
            stockDTO.setSymbol(stockHolding.getSymbol());
            stockDTO.setPurchasePrice(stockHolding.getPurchasePrice());
            stockDTO.setQuantity(stockHolding.getQuantity());

            try {
                List<PublicStockQuote> publicStockQuotes = tradingWebService.getStockQuotes(List.of(stockHolding.getSymbol()));

                for (PublicStockQuote stockQuote : publicStockQuotes) {
                    if (stockQuote.getSymbol().equals(stockHolding.getSymbol())) {
                        stockDTO.setCurrentPrice(stockQuote.getLastTradePrice());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Error fetching stock quotes from trading web service", e);
            }

            stockDTOList.add(stockDTO);
        }

        portfolioDTO.setStocks(stockDTOList);

        return portfolioDTO;
    }
}
