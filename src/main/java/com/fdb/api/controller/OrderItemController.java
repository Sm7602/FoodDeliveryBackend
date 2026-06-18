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
import com.fdb.api.entity.OrderItem;
import com.fdb.api.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/{orderId}/{menuItemId}")
    public OrderItem createOrderItem( @PathVariable Long orderId,@PathVariable Long menuItemId, @RequestBody OrderItem orderItem) {
        System.out.println("OrderItemController.createOrderItem()");
        return orderItemService.createOrderItem(orderId, menuItemId,orderItem);
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id) {
        System.out.println("OrderItemController.getOrderItemById()");
        return orderItemService.getOrderItemById(id);
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        System.out.println("OrderItemController.getAllOrderItems()");
        return orderItemService.getAllOrderItems();
    }

    @PutMapping("/{id}")
    public OrderItem updateOrderItem(@PathVariable Long id,@RequestBody OrderItem orderItem) {
        System.out.println("OrderItemController.updateOrderItem()");
        return orderItemService.updateOrderItem(id, orderItem);
    }

    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        System.out.println("OrderItemController.deleteOrderItem()");
        orderItemService.deleteOrderItem(id);
        return "Order Item Deleted Successfully";
    }
}