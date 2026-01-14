package net.froihofer.dao;

import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.froihofer.entity.Customer;
import net.froihofer.entity.Employee;

public class UserDAO {
    @PersistenceContext(unitName = "ds-finance-bank-ref-persunit")
    private EntityManager entityManager;

    public void persist(Customer customer) {
        entityManager.persist(customer);
    }

    public void persist(Employee employee) {
        entityManager.persist(employee);
    }

    public Customer getCustomerByUsername(String username) {
        String query = "SELECT c FROM Customer c WHERE c.userName = :username";

        Customer customer = entityManager.createQuery(query, Customer.class)
                .setParameter("username", username)
                .getSingleResult();

        return customer;
    }

    public boolean doesCustomerExist(String username) {
        String query = "SELECT c FROM Customer c WHERE c.userName = :username";

        int count = entityManager.createQuery(query, Customer.class)
                .setParameter("username", username)
                .getResultList().size();

        return count > 0;
    }

    public boolean doesEmployeeExist(String username) {
        String query = "SELECT e FROM Employee e WHERE e.userName = :username";

        int count = entityManager.createQuery(query, Employee.class)
                .setParameter("username", username)
                .getResultList().size();

        return count > 0;
    }
}
