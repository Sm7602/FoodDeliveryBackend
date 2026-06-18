package com.fdb.api.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdb.api.entity.Customer;
import com.fdb.api.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
    	System.out.println("CustomerController.createCustomer()");
        return customerService.createCustomer(customer);
    }
 
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
    	System.out.println("CustomerController.getCustomerById()");
    	return customerService.getCustomerById(id);  	
    }
   
    @GetMapping
    public List<Customer> getAllCustomers() {
    	System.out.println("CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id,@RequestBody Customer customer) {
    	System.out.println("CustomerController.updateCustomer()");
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
    	System.out.println("CustomerController.deleteCustomer()");
        customerService.deleteCustomer(id);
        return ("Customer deleted successfully");
    }
    
    @DeleteMapping()
    public String deleteAllCustomer() {
    	System.out.println("CustomerController.deleteAllCustomer()");
        customerService.deleteAllCustomer();
        return ("Customer deleted successfully");
    }
}
