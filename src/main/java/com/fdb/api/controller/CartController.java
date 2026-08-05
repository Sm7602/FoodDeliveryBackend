package com.fdb.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdb.api.dto.cart.CartRequest;
import com.fdb.api.dto.cart.CartResponse;
import com.fdb.api.service.CartService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/createCart")
    public  CartResponse createCart(@Valid @RequestBody CartRequest request) {
        System.out.println("CartController.createCart()");
        return cartService.createCart(request);
    }

    @GetMapping("/getCartByCustomerId")
    public  CartResponse getCartByCustomerId(@Valid @RequestBody CartRequest request) {
        System.out.println("CartController.getCartByCustomerId()");
        return cartService.getCartByCustomerId(request);
    }

    @PutMapping("/addMenuItemToCart")
    public CartResponse addMenuItemToCart(@Valid @RequestBody CartRequest request) {
        System.out.println("CartController.addMenuItemToCart()");
        return cartService.addMenuItemToCart(request);
    }

    @DeleteMapping("/{cartId}")
    public String deleteCart(@PathVariable Long cartId) {
        System.out.println("CartController.deleteCart()");
        cartService.deleteCart(cartId);
        return "Cart Deleted Successfully";
    }
    
    @DeleteMapping("/menu-items")
    public String deleteMenuItemfromCart(@Valid @RequestBody CartRequest request) {
        System.out.println("CartController.deleteMenuItemfromCart()");
        cartService.deleteMenuItemfromCart(request);
        return "MenuItem is Deleted from cart Successfully";
    }
    
    
}
