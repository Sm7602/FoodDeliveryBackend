package com.fdb.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdb.api.dto.foodorder.FoodOrderRequest;
import com.fdb.api.dto.foodorder.FoodOrderResponse;
import com.fdb.api.dto.foodorder.FoodOrderUpdateRequest;
import com.fdb.api.service.FoodOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    @Autowired
    private FoodOrderService foodOrderService;

    @PostMapping
    public FoodOrderResponse createOrder(@Valid @RequestBody FoodOrderRequest request) {
        System.out.println("FoodOrderController.createOrder()");
        return foodOrderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public FoodOrderResponse getOrderById(@PathVariable Long id) {
        System.out.println("FoodOrderController.getOrderById()");
        return foodOrderService.getOrderById(id);
    }

    @GetMapping
    public List<FoodOrderResponse> getAllOrders() {
        System.out.println("FoodOrderController.getAllOrders()");
        return foodOrderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public FoodOrderResponse updateOrder(@PathVariable Long id,@Valid @RequestBody FoodOrderUpdateRequest foodOrder) {
        System.out.println("FoodOrderController.updateOrder()");
        return foodOrderService.updateOrder(id, foodOrder);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        System.out.println("FoodOrderController.deleteOrder()");
        foodOrderService.deleteOrder(id);
        return "Order Deleted Successfully";
    }
}