package net.froihofer.util;

import dto.CustomerDTO;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.inject.Inject;
import net.froihofer.dao.UserDAO;
import net.froihofer.entity.Customer;

import java.security.Principal;

public class SessionUtils {

    @Resource
    private SessionContext sessionContext;

    @Inject
    UserDAO userDAO;


    public boolean isEmployee() {
        return sessionContext.isCallerInRole("employee");
    }

    public Customer getLoggedInCustomer() {
        Principal principal = sessionContext.getCallerPrincipal();

        Customer customer = userDAO.getCustomerByUsername(principal.getName());

        return customer;
    }
}
