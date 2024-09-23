package com.example.plantnursery.controller;

import com.example.plantnursery.DTOs.ItemRequest;
import com.example.plantnursery.DTOs.RemoveItemRequest;
import com.example.plantnursery.model.Cart;
import com.example.plantnursery.model.Orders;
import com.example.plantnursery.service.CartService;
import com.example.plantnursery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {
        return cartService.getCartByCustomer(customerId);
    }

    @PostMapping("/add")
    public Cart addItemToCart(@RequestBody ItemRequest request) {
        return cartService.addItemToCart(request.getCustomerId(), request.getItemId(), request.getQuantity());
    }

    @PutMapping("/increase")
    public Cart increaseItemQuantity(@RequestBody ItemRequest request) {
        return cartService.addItemToCart(request.getCustomerId(), request.getItemId(), request.getQuantity());
    }

    @DeleteMapping("/remove/{customerId}/{itemId}")
    public Cart removeItemFromCart(@PathVariable Long customerId, @PathVariable Long itemId) {
        return cartService.removeItemFromCart(customerId, itemId);
    }


    @PutMapping("/reduce")
    public Cart reduceItemQuantity(@RequestBody ItemRequest request) {
        return cartService.reduceItemQuantity(request.getCustomerId(), request.getItemId(), request.getQuantity());
    }

    @DeleteMapping("/clear/{customerId}")
    public Cart clearCart(@PathVariable Long customerId) {
        return cartService.clearCart(customerId);
    }

    @PostMapping("/checkout/{customerId}")
    public Orders checkout(@PathVariable Long customerId) {
        return orderService.checkout(customerId);
    }
}
