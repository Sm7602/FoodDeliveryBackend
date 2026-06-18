package com.fdb.api.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

	Optional<Cart> findByCustomerId(Long customerId);


	

	

}
