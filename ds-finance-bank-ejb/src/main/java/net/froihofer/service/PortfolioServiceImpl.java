package net.froihofer.service;

import api.PortfolioService;
import dto.PortfolioDTO;
import dto.StockDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.UserDAO;
import net.froihofer.entity.Customer;
import net.froihofer.entity.Portfolio;
import net.froihofer.entity.StockHolding;
import net.froihofer.util.SessionUtils;
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

    @Override
    @RolesAllowed({"customer", "employee"})
    public PortfolioDTO listPortfolio(Long customerId) {
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

            stockDTOList.add(stockDTO);
        }

        portfolioDTO.setStocks(stockDTOList);

        return portfolioDTO;
    }
}
