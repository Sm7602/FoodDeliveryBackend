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

import com.fdb.api.dto.orderitem.OrderItemRequest;
import com.fdb.api.dto.orderitem.OrderItemResponse;
import com.fdb.api.dto.orderitem.OrderItemUpdateRequest;
import com.fdb.api.service.OrderItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public OrderItemResponse createOrderItem(@Valid @RequestBody OrderItemRequest request) {
        System.out.println("OrderItemController.createOrderItem()");
        return orderItemService.createOrderItem(request);
    }

    @GetMapping("/{id}")
    public OrderItemResponse getOrderItemById(@PathVariable Long id) {
        System.out.println("OrderItemController.getOrderItemById()");
        return orderItemService.getOrderItemById(id);
    }

    @GetMapping
    public List<OrderItemResponse> getAllOrderItems() {
        System.out.println("OrderItemController.getAllOrderItems()");
        return orderItemService.getAllOrderItems();
    }

    @PutMapping("/{id}")
    public OrderItemResponse updateOrderItem(@PathVariable Long id,@Valid @RequestBody OrderItemUpdateRequest request) {
        System.out.println("OrderItemController.updateOrderItem()");
        return orderItemService.updateOrderItem(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        System.out.println("OrderItemController.deleteOrderItem()");
        orderItemService.deleteOrderItem(id);
        return "Order Item Deleted Successfully";
    }
}