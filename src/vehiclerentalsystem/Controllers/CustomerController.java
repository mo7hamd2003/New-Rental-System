package vehiclerentalsystem.Controllers;

import vehiclerentalsystem.Services.CustomerService;
import vehiclerentalsystem.Models.Customer;
import java.util.List;

public class CustomerController {
    private CustomerService customerService;

    public CustomerController() {
        this.customerService = new CustomerService();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public boolean addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        return customerService.updateCustomer(customer);
    }

    public boolean deleteCustomer(int id) {
        return customerService.deleteCustomer(id);
    }
}