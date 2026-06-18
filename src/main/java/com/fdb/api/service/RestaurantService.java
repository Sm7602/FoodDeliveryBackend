package com.fdb.api.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.RestaurantRepository;
import com.fdb.api.entity.Restaurant;


@Service
public class  RestaurantService {

	@Autowired
    private  RestaurantRepository restaurantRepository;

   
    public Restaurant createRestaurant(Restaurant restaurant) {
        System.out.println("RestaurantService.createRestaurant()");
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant.setUpdatedAt(LocalDateTime.now());
        return restaurantRepository.save(restaurant);
    }

   
    public List<Restaurant> getAllRestaurants() {
        System.out.println("RestaurantService.getAllRestaurants()");
        return restaurantRepository.findAll();
    }

    
    public Restaurant getRestaurantById(Long id) {
        System.out.println("RestaurantService.getRestaurantById()");
        return restaurantRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));
    }

   
    public Restaurant updateRestaurant(Long id,Restaurant restaurant) {
        System.out.println("RestaurantService.updateRestaurant()");
        Restaurant existingRestaurant =getRestaurantById(id);

        existingRestaurant.setRestaurantName(restaurant.getRestaurantName());
        existingRestaurant.setOwnerName(restaurant.getOwnerName());
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
        existingRestaurant.setRating(restaurant.getRating());
        existingRestaurant.setActive(restaurant.getActive());
        existingRestaurant.setUpdatedAt(LocalDateTime.now());
        return restaurantRepository.save(existingRestaurant);
    }
    
    public List<Restaurant> searchRestaurants(String keyword) {
    	System.out.println("RestaurantService.searchRestaurants()");
        return restaurantRepository.findByrestaurantNameContainingIgnoreCase(keyword);
    } 

    
    public void deleteRestaurant(Long id) {
        System.out.println("RestaurantService.deleteRestaurant()");
        Restaurant restaurant =getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }
    
    public void deleteAllRestaurant() {
        System.out.println("RestaurantService.deleteRestaurant()");
        restaurantRepository.deleteAll();
    }
    
    
}
