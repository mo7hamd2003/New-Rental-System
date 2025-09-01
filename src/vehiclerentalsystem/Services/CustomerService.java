package vehiclerentalsystem.Services;

import vehiclerentalsystem.DAO.CustomerDAO;
import vehiclerentalsystem.Models.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public boolean addCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int id) {
        return customerDAO.deleteCustomer(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}
