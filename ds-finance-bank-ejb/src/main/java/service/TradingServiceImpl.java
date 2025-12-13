package service;

import api.TradingService;
import dto.StockDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

import java.util.List;

@Remote
@Stateless(name = "TradingService")
public class TradingServiceImpl implements TradingService {

    @Override
    @RolesAllowed({"customer"})
    public List<StockDTO> searchStocks(String companyName) {
        System.out.println("Test");
        return List.of();
    }
}
