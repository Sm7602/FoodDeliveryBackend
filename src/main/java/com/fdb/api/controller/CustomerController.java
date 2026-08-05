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
import com.fdb.api.dto.customer.CustomerRequest;
import com.fdb.api.dto.customer.CustomerResponse;
import com.fdb.api.dto.customer.CustomerUpdateRequest;
import com.fdb.api.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest request)  {
    	System.out.println("CustomerController.createCustomer()");
        return customerService.createCustomer(request);
    }
 
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
    	System.out.println("CustomerController.getCustomerById()");
    	return customerService.getCustomerById(id);  	
    }
   
    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
    	System.out.println("CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id,@Valid @RequestBody CustomerUpdateRequest request) {
    	System.out.println("CustomerController.updateCustomer()");
        return customerService.updateCustomer(id, request);
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
