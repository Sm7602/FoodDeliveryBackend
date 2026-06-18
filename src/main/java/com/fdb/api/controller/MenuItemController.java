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
import com.fdb.api.entity.MenuItem;
import com.fdb.api.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/{restaurantId}")
    public MenuItem createMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem) {
        System.out.println("MenuItemController.createMenuItem()");
    	return menuItemService.createMenuItem(restaurantId, menuItem);
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        System.out.println("MenuItemController.getMenuItemById()");
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        System.out.println("MenuItemController.getAllMenuItems()");
        return menuItemService.getAllMenuItems();
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItem> getMenuItemByresturentrant(@PathVariable Long restaurantId) {
        System.out.println("MenuItemController.getMenuItemByRestaurant()");
        return menuItemService.getMenuItemByresturentrant(restaurantId);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id,@RequestBody MenuItem menuItem) {
        System.out.println("MenuItemController.updateMenuItem()");
        return menuItemService.updateMenuItem(id, menuItem);
    }

    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        System.out.println("MenuItemController.deleteMenuItem()");
        menuItemService.deleteMenuItem(id);
        return "Menu Item Deleted Successfully";
    }
}
