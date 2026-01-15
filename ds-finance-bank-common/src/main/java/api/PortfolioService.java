package api;


import dto.PortfolioDTO;

public interface PortfolioService {
    PortfolioDTO listPortfolio(Long customerId);
}
