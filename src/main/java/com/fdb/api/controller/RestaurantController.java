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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdb.api.dto.restaurant.RestaurantRequest;
import com.fdb.api.dto.restaurant.RestaurantResponse;
import com.fdb.api.dto.restaurant.RestaurantUpdateRequest;
import com.fdb.api.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	 @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public RestaurantResponse createRestaurant(@Valid @RequestBody RestaurantRequest request)  {
        System.out.println("RestaurantController.createRestaurant()");
        return restaurantService.createRestaurant(request);
    }

    @GetMapping
    public List<RestaurantResponse> getAllRestaurants() {
        System.out.println("RestaurantController.getAllRestaurants()");
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable Long id) {
        System.out.println("RestaurantController.getRestaurantById()");
        return restaurantService.getRestaurantById(id);
    }

    @PutMapping("/{id}")
    public RestaurantResponse updateRestaurant(@PathVariable Long id,@Valid @RequestBody RestaurantUpdateRequest request) {
        System.out.println("RestaurantController.updateRestaurant()");
    	return restaurantService.updateRestaurant(id, request);
    }
    
    @GetMapping("/search")
    public List<RestaurantResponse> searchRestaurants(@RequestParam String keyword)  {
        System.out.println("RestaurantController.searchRestaurants()");
        return restaurantService.searchRestaurants(keyword);
    }

    @DeleteMapping("/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        System.out.println("RestaurantController.deleteRestaurant()");
        restaurantService.deleteRestaurant(id);
        return "Restaurant deleted successfully";
    }
    
    @DeleteMapping()
    public String deleteALLRestaurant() {
        System.out.println("RestaurantController.deleteALLRestaurant()");
        restaurantService.deleteAllRestaurant();
        return "Restaurants deleted successfully";
    }
    
}