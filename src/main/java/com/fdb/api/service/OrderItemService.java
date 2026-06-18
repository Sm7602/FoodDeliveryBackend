package com.fdb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.FoodOrderRepository;
import com.fdb.api.dao.MenuItemRepository;
import com.fdb.api.dao.OrderItemRepository;
import com.fdb.api.entity.FoodOrder;
import com.fdb.api.entity.MenuItem;
import com.fdb.api.entity.OrderItem;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private FoodOrderRepository foodOrderRepository;
    
    @Autowired
    private MenuItemRepository menuItemRepository;
    
    public OrderItem createOrderItem(Long orderId,Long menuItemId,OrderItem orderItem) {
        System.out.println("OrderItemService.createOrderItem()");
        FoodOrder foodOrder = foodOrderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new RuntimeException("Menu Item not found"));
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setUpdatedAt(LocalDateTime.now());
        orderItem.setActive(true);
        orderItem.setFoodOrder(foodOrder);
        orderItem.setMenuItem(menuItem);
        orderItem.setSubtotal(menuItem.getPrice()
                                .multiply(BigDecimal.valueOf(orderItem.getQuantity())));

               return orderItemRepository.save(orderItem);
}

    public OrderItem getOrderItemById(Long id) {
        System.out.println("OrderItemService.getOrderItemById()");
        return orderItemRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order Item not found"));
    }

    public List<OrderItem> getAllOrderItems() {
        System.out.println("OrderItemService.getAllOrderItems()");
        return orderItemRepository.findAll();
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        System.out.println("OrderItemService.updateOrderItem()");
        OrderItem existingOrderItem = getOrderItemById(id);
        existingOrderItem.setQuantity(orderItem.getQuantity());
        existingOrderItem.setItemPrice(orderItem.getItemPrice());
        existingOrderItem.setSubtotal(orderItem.getItemPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        existingOrderItem.setActive(orderItem.getActive());
        existingOrderItem.setUpdatedAt(LocalDateTime.now());
        return orderItemRepository.save(existingOrderItem);
    }

    public void deleteOrderItem(Long id) {
        System.out.println("OrderItemService.deleteOrderItem()");
        OrderItem orderItem = getOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }
}
