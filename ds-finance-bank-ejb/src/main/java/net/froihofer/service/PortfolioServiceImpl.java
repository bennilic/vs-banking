package net.froihofer.service;

import api.PortfolioService;
import dto.PortfolioDTO;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import org.apache.commons.lang3.NotImplementedException;

@Remote
@Stateless(name = "PortfolioService")
public class PortfolioServiceImpl implements PortfolioService {
    @Override
    public PortfolioDTO listPortfolio() {
        throw new NotImplementedException();
    }
}
