package src.com.kodewala.ecommerce.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import src.com.kodewala.ecommerce.model.Customer;

public class CustomerRepository {
    private final Map<Integer, Customer> customers = new HashMap<>();
// add customer
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }
//find the customer by id
    public Optional<Customer> findByCustomerId(int customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }
// get all customer
    public Map<Integer, Customer> getAllCustomers() {
        return new HashMap<>(customers);
    }
}