package com.fdb.api.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	   
	Optional<User> findByEmail(String email);
}
