package com.fdb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.MenuItemRepository;
import com.fdb.api.dao.RestaurantRepository;
import com.fdb.api.dto.menuitem.MenuItemRequest;
import com.fdb.api.dto.menuitem.MenuItemResponse;
import com.fdb.api.dto.menuitem.MenuItemUpdateRequest;
import com.fdb.api.entity.MenuItem;
import com.fdb.api.entity.Restaurant;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    private MenuItemResponse convertToResponse(MenuItem menuItem) {

        return MenuItemResponse.builder()
        		    .id(menuItem.getId())
        		    .itemName(menuItem.getItemName())
        		    .description(menuItem.getDescription())
        		    .category(menuItem.getCategory())
        		    .price(menuItem.getPrice())
        		    .available(menuItem.getAvailable())
        		    .createdAt(menuItem.getCreatedAt())
                .updatedAt(menuItem.getUpdatedAt())       
                .restaurant(menuItem.getRestaurant())
                .build();
    }

    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        System.out.println("MenuItemService.createMenuItem()");
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));
        
        MenuItem menuItem=MenuItem.builder()
        		.itemName(request.getItemName())
    		    .description(request.getDescription())
    		    .category(request.getCategory())
    		    .price(request.getPrice())
    		    .available(true)
    		    .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())       
            .restaurant(restaurant)
        		.build();
        
        menuItem= menuItemRepository.save(menuItem);
        
        return convertToResponse(menuItem);
    }

    public MenuItemResponse getMenuItemById(Long id) {
        System.out.println("MenuItemService.getMenuItemById()");
        MenuItem menuItem= menuItemRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Menu Item not found"));
        return convertToResponse(menuItem);
    }

    public List<MenuItemResponse> getAllMenuItems() {
        System.out.println("MenuItemService.getAllMenuItems()");
        return menuItemRepository.findAll()
        		     .stream()
                 .map(this::convertToResponse)
                 .toList();
    }
    
    public List<MenuItemResponse> getMenuItemByresturentrant(Long restaurantId) {
        System.out.println("MenuItemService.getMenuItemByresturent()");
        Restaurant restaurant=restaurantRepository.findById(restaurantId).orElseThrow(() ->
        new RuntimeException("Restaurant not found"));
        
        return menuItemRepository.findByrestaurant(restaurant)
        		    .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public MenuItemResponse updateMenuItem(Long id,MenuItemUpdateRequest request) {
        System.out.println("MenuItemService.updateMenuItem()");
        MenuItem menuItem= menuItemRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Menu Item not found"));

        menuItem.setItemName(request.getItemName());
        menuItem.setDescription(request.getDescription());
        menuItem.setCategory(request.getCategory());
        menuItem.setPrice(request.getPrice());
        menuItem.setAvailable(request.getAvailable());
        menuItem.setUpdatedAt(LocalDateTime.now());

        menuItem= menuItemRepository.save(menuItem);
        
        return convertToResponse(menuItem);
    }

    public void deleteMenuItem(Long id) {
        System.out.println("MenuItemService.deleteMenuItem()");
        MenuItem menuItem= menuItemRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Menu Item not found"));
        menuItemRepository.delete(menuItem);
    }
}
