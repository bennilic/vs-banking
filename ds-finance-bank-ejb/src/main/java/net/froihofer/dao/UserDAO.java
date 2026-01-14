package net.froihofer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.froihofer.entity.Customer;

import java.util.List;

public class UserDAO {
    @PersistenceContext(unitName = "ds-finance-bank-ref-persunit")
    private EntityManager entityManager;

    public List<Customer> findCustomerbyName(String name){
        String query = "SELECT c FROM Customer c WHERE (LOWER(c.firstName) LIKE LOWER(:name) OR LOWER(c.lastName) LIKE LOWER(:name))";
        return entityManager.createQuery(query,Customer.class).setParameter("name","%" +name + "%").getResultList();
    }

    public Customer findById(long id){
        Customer custom = entityManager.find(Customer.class, id);
        if(custom == null)
        {
            System.err.println("ID " + id + " not found");
        } else {
            System.out.println("Customer with " + id + ": " +custom.getUsername() );
        }
        return custom;
    }

    public void persist(Customer customer) {
        entityManager.persist(customer);
    }
}
