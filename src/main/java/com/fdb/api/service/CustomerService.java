package com.fdb.api.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.CustomerRepository;
import com.fdb.api.entity.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    
    public Customer createCustomer(Customer customer) {
    	System.out.println("CustomerService.createCustomer()");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setActive(true);
        return customerRepository.save(customer);
    }


    public Customer getCustomerById(Long id) {
    	System.out.println("CustomerService.getCustomerById()");
        return customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found with id : " + id));
    }

    
    public List<Customer> getAllCustomers() {
    	System.out.println("CustomerService.getAllCustomers()");
        return customerRepository.findByActiveTrue();
    }


    public Customer updateCustomer(Long id, Customer customer) {
    	System.out.println("CustomerService.updateCustomer()");
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found with id : " + id));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(existingCustomer);
    }


    public void deleteCustomer(Long id) {
        System.out.println("CustomerService.deleteCustomer()");
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found with id : " + id));
        customerRepository.delete(customer);
    }
    
    public void deleteAllCustomer() {
        System.out.println("CustomerService.deleteAllCustomer()");
        customerRepository.deleteAll();
    }
}