package com.fdb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.FoodOrderRepository;
import com.fdb.api.entity.FoodOrder;

@Service
public class FoodOrderService {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    public FoodOrder createOrder(FoodOrder foodOrder) {
        System.out.println("FoodOrderService.createOrder()");
        foodOrder.setOrderTime(LocalDateTime.now());
        foodOrder.setUpdatedAt(LocalDateTime.now());
        return foodOrderRepository.save(foodOrder);
    }

    public FoodOrder getOrderById(Long id) {
        System.out.println("FoodOrderService.getOrderById()");
        return foodOrderRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public List<FoodOrder> getAllOrders() {
        System.out.println("FoodOrderService.getAllOrders()");
        return foodOrderRepository.findAll();
    }

    public FoodOrder updateOrder(Long id,FoodOrder foodOrder) {
        System.out.println("FoodOrderService.updateOrder()");
        FoodOrder existingOrder = getOrderById(id);
        existingOrder.setOrderStatus(foodOrder.getOrderStatus());
        existingOrder.setDeliveryAddress(foodOrder.getDeliveryAddress());
        existingOrder.setTotalAmount(foodOrder.getTotalAmount());
        existingOrder.setUpdatedAt(LocalDateTime.now());
        return foodOrderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        System.out.println("FoodOrderService.deleteOrder()");
        FoodOrder order = getOrderById(id);
        foodOrderRepository.delete(order);
    }
}
