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
import com.fdb.api.dto.menuitem.MenuItemRequest;
import com.fdb.api.dto.menuitem.MenuItemResponse;
import com.fdb.api.dto.menuitem.MenuItemUpdateRequest;
import com.fdb.api.service.MenuItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/{restaurantId}")
    public MenuItemResponse createMenuItem(@Valid @RequestBody MenuItemRequest request)  {
        System.out.println("MenuItemController.createMenuItem()");
    	return menuItemService.createMenuItem(request);
    }

    @GetMapping("/{id}")
    public MenuItemResponse getMenuItemById(@PathVariable Long id) {
        System.out.println("MenuItemController.getMenuItemById()");
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping
    public List<MenuItemResponse> getAllMenuItems() {
        System.out.println("MenuItemController.getAllMenuItems()");
        return menuItemService.getAllMenuItems();
    }
    
    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItemResponse> getMenuItemByresturentrant(@PathVariable Long restaurantId) {
        System.out.println("MenuItemController.getMenuItemByRestaurant()");
        return menuItemService.getMenuItemByresturentrant(restaurantId);
    }

    @PutMapping("/{id}")
    public MenuItemResponse updateMenuItem(@PathVariable Long id,@Valid @RequestBody MenuItemUpdateRequest request) {
        System.out.println("MenuItemController.updateMenuItem()");
        return menuItemService.updateMenuItem(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        System.out.println("MenuItemController.deleteMenuItem()");
        menuItemService.deleteMenuItem(id);
        return "Menu Item Deleted Successfully";
    }
}
