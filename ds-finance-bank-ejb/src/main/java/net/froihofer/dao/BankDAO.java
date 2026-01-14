package net.froihofer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.froihofer.entity.Bank;

public class BankDAO {
    @PersistenceContext(unitName = "ds-finance-bank-ref-persunit")
    private EntityManager entityManager;

    public void persist(Bank bank) {
        entityManager.persist(bank);
    }

    public Bank findById(long id) {
        return entityManager.find(Bank.class, id);
    }
}
