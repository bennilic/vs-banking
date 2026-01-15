package api;

import dto.CustomerDTO;
import jakarta.ejb.Remote;

import java.util.List;

public interface CustomerService {
    void createCustomer(CustomerDTO customer);

    CustomerDTO findCustomer(long customerId);

    CustomerDTO findCustomer(String query);

    boolean isEmployee();
}
