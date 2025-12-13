package net.froihofer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.froihofer.entity.StockHolding;

public class StockHoldingDAO {
    @PersistenceContext(unitName = "ds-finance-bank-ref-persunit")
    private EntityManager entityManager;

    public void persist(StockHolding stockHolding) {
        entityManager.persist(stockHolding);
    }
}
