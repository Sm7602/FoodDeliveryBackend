package com.fdb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
