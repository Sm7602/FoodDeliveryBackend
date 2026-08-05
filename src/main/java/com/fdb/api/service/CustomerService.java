package com.fdb.api.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.CustomerRepository;
import com.fdb.api.dto.customer.CustomerRequest;
import com.fdb.api.dto.customer.CustomerResponse;
import com.fdb.api.dto.customer.CustomerUpdateRequest;
import com.fdb.api.entity.Customer;
import com.fdb.api.entity.User;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    private CustomerResponse convertToResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .active(customer.getActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .orders(customer.getOrders())
                .cart(customer.getCart())
                .userId(customer.getUser().getId())
                .build();
    }

    
    public CustomerResponse createCustomer(CustomerRequest request) {
    	System.out.println("CustomerService.createCustomer()");
   
    	User user=User.builder()
    			.id(request.getUserId())
    			.build();
    	
    	Customer customer=Customer.builder()
				.firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .active(true)
                .build();
		
		customer=customerRepository.save(customer);
		return convertToResponse(customer);
	
    }


    public CustomerResponse getCustomerById(Long id) {
    	System.out.println("CustomerService.getCustomerById()");
    	Customer customer= customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found with id : " + id));
    	return convertToResponse(customer);
    }

    
    public List<CustomerResponse> getAllCustomers() {
    	System.out.println("CustomerService.getAllCustomers()");
        return customerRepository.findByActiveTrue()
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }


    public CustomerResponse  updateCustomer(long id,CustomerUpdateRequest request)  {
    	System.out.println("CustomerService.updateCustomer()");
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found with id : " + id));

        existingCustomer.setFirstName(request.getFirstName());
        existingCustomer.setLastName(request.getLastName());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        existingCustomer.setAddress(request.getAddress());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        existingCustomer=customerRepository.save(existingCustomer);
		return convertToResponse(existingCustomer);
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