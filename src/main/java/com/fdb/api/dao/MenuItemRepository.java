package com.fdb.api.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.MenuItem;
import com.fdb.api.entity.Restaurant;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long>{

	List<MenuItem> findByrestaurant(Restaurant restaurant);

	

}
