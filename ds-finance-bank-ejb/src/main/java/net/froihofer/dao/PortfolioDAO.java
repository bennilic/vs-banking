package net.froihofer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.froihofer.entity.Portfolio;
import net.froihofer.entity.StockHolding;

public class PortfolioDAO {
    @PersistenceContext(unitName = "ds-finance-bank-ref-persunit")
    private EntityManager entityManager;

    public void persist(StockHolding stockHolding) {
        entityManager.persist(stockHolding);
    }

    public void persist(Portfolio portfolio) {
        entityManager.persist(portfolio);
    }
}
