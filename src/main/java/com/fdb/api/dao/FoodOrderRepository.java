package com.fdb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.FoodOrder;

public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {

}
