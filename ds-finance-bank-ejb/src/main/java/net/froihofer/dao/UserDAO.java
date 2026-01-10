package net.froihofer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.froihofer.entity.Customer;

public class UserDAO {
    @PersistenceContext(unitName = "ds-finance-bank-ref-persunit")
    private EntityManager entityManager;

    public void persist(Customer customer) {
        entityManager.persist(customer);
    }
}
