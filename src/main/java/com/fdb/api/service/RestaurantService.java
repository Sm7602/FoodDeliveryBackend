package com.fdb.api.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.RestaurantRepository;
import com.fdb.api.dto.restaurant.RestaurantRequest;
import com.fdb.api.dto.restaurant.RestaurantResponse;
import com.fdb.api.dto.restaurant.RestaurantUpdateRequest;
import com.fdb.api.entity.Restaurant;
import com.fdb.api.entity.User;


@Service
public class  RestaurantService {

	@Autowired
    private  RestaurantRepository restaurantRepository;
	
	 private RestaurantResponse convertToResponse(Restaurant restaurant) {

	        return RestaurantResponse.builder()
	        		    .id(restaurant.getId())
	        		    .restaurantName(restaurant.getRestaurantName())
	        		    .ownerName(restaurant.getOwnerName())
	        		    .address(restaurant.getAddress())
	        		    .phoneNumber(restaurant.getPhoneNumber())
	        		    .rating(restaurant.getRating())
	        		    .active(restaurant.getActive())
	        		    .updatedAt(restaurant.getUpdatedAt())
	        		    .createdAt(restaurant.getCreatedAt())
	        		    .menuItems(restaurant.getMenuItems())
	        		    .orders(restaurant.getOrders())
	        		    .user(restaurant.getUser())
	                .build();
	    }

   
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        System.out.println("RestaurantService.createRestaurant()");


     	User user=User.builder()
    			.id(request.getUserId())
    			.build();
     	
        Restaurant restaurant=Restaurant.builder()
        		    .restaurantName(request.getRestaurantName())
     		    .ownerName(request.getOwnerName())
     		    .address(request.getAddress())
     		    .phoneNumber(request.getPhoneNumber())
     		    .rating(request.getRating())
     		    .active(true)
   		        .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
     		    .user(user)
        		    .build();
        
        restaurant= restaurantRepository.save(restaurant);
        
        return convertToResponse(restaurant);
    }

   
    public List<RestaurantResponse> getAllRestaurants() {
        System.out.println("RestaurantService.getAllRestaurants()");
        return restaurantRepository.findAll()
        		    .stream()
                .map(this::convertToResponse)
                .toList();
    }

    
    public RestaurantResponse getRestaurantById(Long id) {
        System.out.println("RestaurantService.getRestaurantById()");
        Restaurant restaurant=restaurantRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));
        
        return convertToResponse(restaurant);
    }

   
    public RestaurantResponse updateRestaurant(Long id,RestaurantUpdateRequest request) {
        System.out.println("RestaurantService.updateRestaurant()");
       
        Restaurant existingRestaurant=restaurantRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Restaurant not found"));

        existingRestaurant.setRestaurantName(request.getRestaurantName());
        existingRestaurant.setOwnerName(request.getOwnerName());
        existingRestaurant.setAddress(request.getAddress());
        existingRestaurant.setPhoneNumber(request.getPhoneNumber());
        existingRestaurant.setRating(request.getRating());
        existingRestaurant.setActive(request.getActive());
        existingRestaurant.setUpdatedAt(LocalDateTime.now());
        
        existingRestaurant= restaurantRepository.save(existingRestaurant);
        
        return convertToResponse(existingRestaurant);
    }
    
    public List<RestaurantResponse> searchRestaurants(String keyword) {
    	System.out.println("RestaurantService.searchRestaurants()");
        return restaurantRepository.findByrestaurantNameContainingIgnoreCase(keyword)
        		    .stream()
                .map(this::convertToResponse)
                .toList();
    } 

    
    public void deleteRestaurant(Long id) {
        System.out.println("RestaurantService.deleteRestaurant()");
        Restaurant restaurant=restaurantRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }
    
    public void deleteAllRestaurant() {
        System.out.println("RestaurantService.deleteRestaurant()");
        restaurantRepository.deleteAll();
    }
    
    
}
