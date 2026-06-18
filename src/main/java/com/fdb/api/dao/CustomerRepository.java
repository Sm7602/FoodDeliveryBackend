package com.fdb.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

	List<Customer> findByActiveTrue();

}
