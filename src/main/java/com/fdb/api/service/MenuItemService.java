package com.fdb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.MenuItemRepository;
import com.fdb.api.dao.RestaurantRepository;
import com.fdb.api.entity.MenuItem;
import com.fdb.api.entity.Restaurant;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public MenuItem createMenuItem(Long restaurantId,MenuItem menuItem) {
        System.out.println("MenuItemService.createMenuItem()");
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));
        menuItem.setRestaurant(restaurant);
        menuItem.setCreatedAt(LocalDateTime.now());
        menuItem.setUpdatedAt(LocalDateTime.now());
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemById(Long id) {
        System.out.println("MenuItemService.getMenuItemById()");
        return menuItemRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Menu Item not found"));
    }

    public List<MenuItem> getAllMenuItems() {
        System.out.println("MenuItemService.getAllMenuItems()");
        return menuItemRepository.findAll();
    }
    
    public List<MenuItem> getMenuItemByresturentrant(Long restaurantId) {
        System.out.println("MenuItemService.getMenuItemByresturent()");
        Restaurant restaurant=restaurantRepository.findById(restaurantId).orElseThrow(() ->
        new RuntimeException("Restaurant not found"));
        return menuItemRepository.findByrestaurant(restaurant);
    }

    public MenuItem updateMenuItem(Long id,MenuItem menuItem) {
        System.out.println("MenuItemService.updateMenuItem()");
        MenuItem existingMenuItem = getMenuItemById(id);

        existingMenuItem.setItemName(menuItem.getItemName());
        existingMenuItem.setDescription(menuItem.getDescription());
        existingMenuItem.setCategory(menuItem.getCategory());
        existingMenuItem.setPrice(menuItem.getPrice());
        existingMenuItem.setAvailable(menuItem.getAvailable());
        existingMenuItem.setUpdatedAt(LocalDateTime.now());

        return menuItemRepository.save(existingMenuItem);
    }

    public void deleteMenuItem(Long id) {
        System.out.println("MenuItemService.deleteMenuItem()");
        MenuItem menuItem = getMenuItemById(id);
        menuItemRepository.delete(menuItem);
    }
}
