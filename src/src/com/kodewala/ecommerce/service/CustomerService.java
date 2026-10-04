package src.com.kodewala.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import Exceptions.CustomerNotFoundException;
import src.com.kodewala.ecommerce.model.Customer;
import src.com.kodewala.ecommerce.repository.CustomerRepository;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerCustomer(Customer customer) {
        customerRepository.addCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " does not exist."));
    }
// update the address
    public void updateCustomerAddress(int customerId, String newAddress) {
        Customer customer = getCustomerById(customerId);
        customer.setAddress(newAddress);
    }
// all customer .
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerRepository.getAllCustomers().values());
    }
}