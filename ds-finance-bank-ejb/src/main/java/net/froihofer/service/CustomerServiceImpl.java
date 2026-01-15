package net.froihofer.service;

import api.CustomerService;
import dto.CustomerDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.UserDAO;
import net.froihofer.entity.Customer;
import net.froihofer.entity.User;
import net.froihofer.util.SessionUtils;
import net.froihofer.util.jboss.WildflyAuthDBHelper;

@Remote
@Stateless(name = "CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @Inject
    UserDAO userDao;

    @Inject
    WildflyAuthDBHelper wildflyAuthDBHelper;

    @Inject
    SessionUtils sessionUtils;

    private CustomerDTO getCustomer (User name){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(name.getId());
        customerDTO.setLastName(name.getLastName());
        customerDTO.setFirstName(name.getFirstName());
        customerDTO.setUserName(name.getUsername());

        return customerDTO;
    }
    @RolesAllowed("employee")
    public void createCustomer(CustomerDTO customer) {
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String username = customer.getUserName();
        String password = customer.getPassword();
        String address = customer.getAddress();

        Customer c = new Customer(
                firstName,
                lastName,
                username,
                password,
                address
        );

        userDao.persist(c);

        try {
            wildflyAuthDBHelper.addUser(username, password, new String[]{"customer"});
        } catch (Exception e) {
            throw new RuntimeException("Unable to add customer to Wildfly DB", e);
        }
    }

    @Override
    @RolesAllowed("employee")
    public CustomerDTO findCustomer(long customerId) {

        Customer custom = userDao.findById(customerId);
        if (custom == null){
            return null;
        }
        return getCustomer(custom);

    }

    @Override
    @RolesAllowed("employee")
    public CustomerDTO findCustomer(String username) {
       Customer customer = userDao.getCustomerByUsername(username);

       return getCustomer(customer);
    }


    @Override
    @RolesAllowed({"employee", "customer"})
    public boolean isEmployee() {
        return sessionUtils.isEmployee();
    }
}
