package com.fdb.api.service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.CartRepository;
import com.fdb.api.dao.CustomerRepository;
import com.fdb.api.dao.MenuItemRepository;
import com.fdb.api.entity.Cart;
import com.fdb.api.entity.Customer;
import com.fdb.api.entity.MenuItem;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public Cart createCart(Long customerId) {
        System.out.println("CartService.createCart()");
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Cart cart = new Cart();

        cart.setCustomer(customer);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cart.setActive(true);
        return cartRepository.save(cart);
    }

    
        public Cart getCartByCustomerId(Long customerId) {
        	System.out.println("CartService.getCartByCustomerId()");
            return cartRepository.findByCustomerId(customerId).orElseThrow(() ->
                            new RuntimeException("Cart not found"));
        }
   

    public Cart addMenuItemToCart(Long cartId,Long menuItemId) {
    	System.out.println("CartService.addMenuItemToCart()");
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                        new RuntimeException("Menu Item not found"));

        cart.getMenuItems().add(menuItem);
        cart.setTotalAmount(cart.getTotalAmount().add(menuItem.getPrice()));
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    public void deleteCart(Long cartId) {
    	System.out.println("CartService.deleteCart()");
    	 Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
    	                    new RuntimeException("Cart not found"));
    	    Customer customer = cart.getCustomer();
    	    if(customer != null) {
    	        customer.setCart(null);
    	        customerRepository.save(customer);
    	    }

    	    cartRepository.delete(cart);
    }
    
    public Cart deleteMenuItemfromCart(Long cartId,Long menuItemId) {
    	System.out.println("CartService.deleteMenuItemfromCart()");
    	    Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
    	                    new RuntimeException("Cart not found"));

    	    MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
    	                    new RuntimeException("Menu Item not found"));

    	    cart.getMenuItems().remove(menuItem);
    	    cart.setTotalAmount(cart.getTotalAmount().subtract(menuItem.getPrice()));
    	    cart.setUpdatedAt(LocalDateTime.now());
    	    return cartRepository.save(cart);
    }
    
    
}
