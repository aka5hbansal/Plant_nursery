package com.example.plantnursery.service;

import com.example.plantnursery.model.*;
import com.example.plantnursery.repository.CartRepository;
import com.example.plantnursery.repository.CartItemRepository;
import com.example.plantnursery.repository.OrderRepository;
import com.example.plantnursery.repository.OrderItemRepository;
import com.example.plantnursery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Cart getCartByCustomer(Long customerId) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });
    }

    public Cart addItemToCart(Long customerId, Long itemId, int quantity) {
        Cart cart = getCartByCustomer(customerId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(ci -> ci.getItem().getItemId().equals(itemId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setItem(item);
            newCartItem.setQuantity(quantity);
            cart.getItems().add(newCartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        return cartRepository.save(cart);
    }

    public Cart reduceItemQuantity(Long customerId, Long itemId, int quantity) {
        Cart cart = getCartByCustomer(customerId);
        CartItem cartItem = cart.getItems().stream()
                .filter(ci -> ci.getItem().getItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        int newQuantity = cartItem.getQuantity() - quantity;
        if (newQuantity <= 0) {
            cart.getItems().remove(cartItem);
        } else {
            cartItem.setQuantity(newQuantity);
        }

        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long customerId, Long itemId) {
        Cart cart = getCartByCustomer(customerId);
        CartItem cartItem = cart.getItems().stream()
                .filter(ci -> ci.getItem().getItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cart.getItems().remove(cartItem);
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long customerId) {
        Cart cart = getCartByCustomer(customerId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

}
