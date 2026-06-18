package com.fdb.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fdb.api.entity.Cart;
import com.fdb.api.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/customer/{customerId}")
    public Cart createCart(@PathVariable Long customerId) {
        System.out.println("CartController.createCart()");
        return cartService.createCart(customerId);
    }

    @GetMapping("/customer/{customerId}")
    public Cart getCartByCustomerId(@PathVariable Long customerId) {
        System.out.println("CartController.getCartByCustomerId()");
        return cartService.getCartByCustomerId(customerId);
    }

    @PutMapping
    public Cart getCartByCustomerId(@RequestParam Long cartId,@RequestParam Long menuItemId) {
        System.out.println("CartController.getCartByCustomerId()");
        return cartService.addMenuItemToCart(cartId, menuItemId);
    }

    @DeleteMapping("/{cartId}")
    public String deleteCart(@PathVariable Long cartId) {
        System.out.println("CartController.deleteCart()");
        cartService.deleteCart(cartId);
        return "Cart Deleted Successfully";
    }
    
    @DeleteMapping("/menu-items")
    public String deleteMenuItemfromCart(@RequestParam Long cartId,@RequestParam Long menuItemId) {
        System.out.println("CartController.deleteMenuItemfromCart()");
        cartService.deleteMenuItemfromCart(cartId, menuItemId);
        return "MenuItem is Deleted from cart Successfully";
    }
    
    
}
