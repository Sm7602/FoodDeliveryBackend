package com.fdb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdb.api.dao.CustomerRepository;
import com.fdb.api.dao.DeliveryPartnerRepository;
import com.fdb.api.dao.FoodOrderRepository;
import com.fdb.api.dao.RestaurantRepository;
import com.fdb.api.dto.foodorder.FoodOrderRequest;
import com.fdb.api.dto.foodorder.FoodOrderResponse;
import com.fdb.api.dto.foodorder.FoodOrderUpdateRequest;
import com.fdb.api.entity.Customer;
import com.fdb.api.entity.DeliveryPartner;
import com.fdb.api.entity.FoodOrder;
import com.fdb.api.entity.Restaurant;

@Service
public class FoodOrderService {

    @Autowired
    private FoodOrderRepository foodOrderRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;
    
    private FoodOrderResponse convertToResponse(FoodOrder order) {

        return FoodOrderResponse.builder()
        		    .id(order.getId())
        		    .orderNumber(order.getOrderNumber())
        		    .totalAmount(order.getTotalAmount())
        		    .deliveryAddress(order.getDeliveryAddress())
        		    .orderStatus(order.getOrderStatus())
        		    .orderTime(order.getOrderTime())
                .updatedAt(order.getUpdatedAt())
                .customer(order.getCustomer())       
                .restaurant(order.getRestaurant())
                .deliveryPartner(order.getDeliveryPartner())
                .orderItems(order.getOrderItems())
                .build();
    }

    public FoodOrderResponse createOrder(FoodOrderRequest request) {
        System.out.println("FoodOrderService.createOrder()");
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        
        Restaurant restaurant=restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() ->
                new RuntimeException("restaurant not found"));
        
        DeliveryPartner deliveryPartner=deliveryPartnerRepository.findById(request.getDeliveryPartnerId())
                .orElseThrow(() ->
                new RuntimeException("DeliveryPartner not found"));
        
        FoodOrder foodOrder=FoodOrder.builder()
        		                .orderNumber("ORD-"+System.currentTimeMillis())
    		                    .totalAmount(BigDecimal.ZERO)
        		                .orderStatus("Pending")
        		                .deliveryAddress(request.getDeliveryAddress())
        		                .orderTime(LocalDateTime.now())
        		                .updatedAt(LocalDateTime.now())
        		                .customer(customer)
        		                .restaurant(restaurant)
        		                .deliveryPartner(deliveryPartner)
        		                .build();
        
        foodOrder= foodOrderRepository.save(foodOrder);
        return convertToResponse(foodOrder);
    }

    public FoodOrderResponse getOrderById(Long id) {
        System.out.println("FoodOrderService.getOrderById()");
        FoodOrder foodOrder=foodOrderRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Order not found"));
        
        return convertToResponse(foodOrder);
    }

    public List<FoodOrderResponse> getAllOrders() {
        System.out.println("FoodOrderService.getAllOrders()");
        return foodOrderRepository.findAll() 
        		    .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public FoodOrderResponse updateOrder(Long id,FoodOrderUpdateRequest foodOrder) {
        System.out.println("FoodOrderService.updateOrder()");
        FoodOrder existingOrder = foodOrderRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Order not found"));
        
        existingOrder.setOrderStatus(foodOrder.getOrderStatus());
        existingOrder.setDeliveryAddress(foodOrder.getDeliveryAddress());
        existingOrder.setTotalAmount(foodOrder.getTotalAmount());
        existingOrder.setUpdatedAt(LocalDateTime.now());
      
        existingOrder= foodOrderRepository.save(existingOrder);
        return convertToResponse(existingOrder);
    }

    public void deleteOrder(Long id) {
        System.out.println("FoodOrderService.deleteOrder()");
        FoodOrder order = foodOrderRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Order not found"));
        
        foodOrderRepository.delete(order);
    }
}
