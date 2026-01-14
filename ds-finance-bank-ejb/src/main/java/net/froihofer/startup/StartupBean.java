package net.froihofer.startup;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import net.froihofer.dao.BankDAO;
import net.froihofer.dao.UserDAO;
import net.froihofer.entity.Bank;
import net.froihofer.entity.Customer;
import net.froihofer.entity.Employee;
import net.froihofer.util.jboss.WildflyAuthDBHelper;

import java.math.BigDecimal;

@Singleton
@Startup
public class StartupBean {

    @Inject
    private BankDAO bankDao;

    @Inject
    private UserDAO userDAO;

    @Inject
    WildflyAuthDBHelper wildflyAuthDBHelper;

    @PostConstruct
    private void init() {
        createBankIfNotExists();
        createDefaultEmployeeIfNotExists();
        createDefaultCustomerIfNotExists();
    }

    public void createBankIfNotExists() {
        Bank existingBank = bankDao.findById(1);

        if (existingBank == null) {
            Bank bank = new Bank();
            bank.setInvestableVolume(BigDecimal.valueOf(1_000_000_000L));

            bankDao.persist(bank);
        }
    }

    public void createDefaultEmployeeIfNotExists() {
        if (!userDAO.doesEmployeeExist("default-e")) {
            try {
                wildflyAuthDBHelper.addUser("default-e", "super-secure", new String[]{"employee"});

                String firstName = "Default";
                String lastName = "Employee";
                String username = "default-e";
                String password = "super-secure";

                Employee e = new Employee(
                        firstName,
                        lastName,
                        username,
                        password
                );

                userDAO.persist(e);
            } catch (Exception e) {
                throw new RuntimeException("Unable to add a default employee to Wildfly DB", e);
            }
        }
    }

    public void createDefaultCustomerIfNotExists() {
        if (!userDAO.doesCustomerExist("default-c")) {
            try {
                wildflyAuthDBHelper.addUser("default-c", "super-secure", new String[]{"customer"});

                String firstName = "Default";
                String lastName = "Customer";
                String username = "default-c";
                String password = "super-secure";
                String address = "-";

                Customer c = new Customer(
                        firstName,
                        lastName,
                        username,
                        password,
                        address
                );

                userDAO.persist(c);
            } catch (Exception e) {
                throw new RuntimeException("Unable to add a default customer to Wildfly DB", e);
            }
        }
    }
}
