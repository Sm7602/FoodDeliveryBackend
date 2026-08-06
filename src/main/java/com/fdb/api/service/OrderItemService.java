package com.fdb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.FoodOrderRepository;
import com.fdb.api.dao.MenuItemRepository;
import com.fdb.api.dao.OrderItemRepository;
import com.fdb.api.dto.orderitem.OrderItemRequest;
import com.fdb.api.dto.orderitem.OrderItemResponse;
import com.fdb.api.dto.orderitem.OrderItemUpdateRequest;
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
    
    private OrderItemResponse convertToResponse(OrderItem orderItem) {

        return OrderItemResponse.builder()
        		    .id(orderItem.getId())
        		    .quantity(orderItem.getQuantity())
        		    .itemPrice(orderItem.getItemPrice())
        		    .subtotal(orderItem.getSubtotal())
        		    .active(orderItem.getActive())
        		    .updatedAt(orderItem.getUpdatedAt())
        		    .createdAt(orderItem.getCreatedAt())
        		    .menuItem(orderItem.getMenuItem())
        		    .foodOrder(orderItem.getFoodOrder())
                .build();
    }
    
    public OrderItemResponse createOrderItem(OrderItemRequest request) {
        System.out.println("OrderItemService.createOrderItem()");
        FoodOrder foodOrder = foodOrderRepository.findById(request.getFoodOrderId())
                        .orElseThrow(() -> new RuntimeException("Order not found"));

        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                        .orElseThrow(() -> new RuntimeException("Menu Item not found"));
        
        OrderItem orderItem=OrderItem.builder()
        		.quantity(request.getQuantity())
    		    .itemPrice(request.getItemPrice())
    		    .subtotal((menuItem.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))))
    		    .active(true)
    		    .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
    		    .menuItem(menuItem)
    		    .foodOrder(foodOrder)
        		.build();

        orderItem= orderItemRepository.save(orderItem);
        return convertToResponse(orderItem);
    }

    public OrderItemResponse getOrderItemById(Long id) {
        System.out.println("OrderItemService.getOrderItemById()");
        OrderItem orderItem=orderItemRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order Item not found"));
        return convertToResponse(orderItem);
    }

    public List<OrderItemResponse> getAllOrderItems() {
        System.out.println("OrderItemService.getAllOrderItems()");
        return orderItemRepository.findAll()
        		     .stream()
                 .map(this::convertToResponse)
                 .toList();
    }

    public OrderItemResponse updateOrderItem(Long id, OrderItemUpdateRequest request) {
        System.out.println("OrderItemService.updateOrderItem()");
        OrderItem existingOrderItem = orderItemRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Order Item not found"));
        
        existingOrderItem.setQuantity(request.getQuantity());
        existingOrderItem.setItemPrice(request.getItemPrice());
        existingOrderItem.setSubtotal(request.getItemPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity())));
        existingOrderItem.setActive(request.getActive());
        existingOrderItem.setUpdatedAt(LocalDateTime.now());

        existingOrderItem= orderItemRepository.save(existingOrderItem);
        return convertToResponse(existingOrderItem);
  }

    public void deleteOrderItem(Long id) {
        System.out.println("OrderItemService.deleteOrderItem()");
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Order Item not found"));
        orderItemRepository.delete(orderItem);
    }
}
