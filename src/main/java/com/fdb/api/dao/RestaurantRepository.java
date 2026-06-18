package com.fdb.api.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.fdb.api.entity.Restaurant;

@EnableJpaRepositories
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

	List<Restaurant> findByrestaurantNameContainingIgnoreCase(String keyword);


}
