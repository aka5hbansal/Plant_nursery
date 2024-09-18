package com.example.plantnursery.service;

import com.example.plantnursery.DTOs.DeliveryRequest;
import com.example.plantnursery.model.*;
import com.example.plantnursery.repository.CartRepository;
import com.example.plantnursery.repository.CartItemRepository;
import com.example.plantnursery.repository.ItemRepository;
import com.example.plantnursery.repository.OrderRepository;
import com.example.plantnursery.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Orders checkout(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        double totalAmount = cart.getItems().stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getItem().getPrice())
                .sum();

        Orders order = new Orders();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setItem(cartItem.getItem());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getItem().getPrice());
                    return orderItem;
                }).collect(Collectors.toList());

        order.setItems(orderItems);

        cart.getItems().clear();

        cartRepository.save(cart);
        return orderRepository.save(order);
    }
    public Orders getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void updateDeliveryDetails(Long orderId, DeliveryRequest deliveryRequest) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (deliveryRequest.getDeliveryMethod() == Orders.DeliveryMethod.SHIPPING) {
            order.setAddress(deliveryRequest.getAddress());
            order.setStoreId(null);  // Make sure storeId is null
        } else if (deliveryRequest.getDeliveryMethod() == Orders.DeliveryMethod.PICKUP) {
            order.setStoreId(deliveryRequest.getStoreId());
            order.setAddress(null);  // Make sure address is null
        }
        order.setDeliveryMethod(deliveryRequest.getDeliveryMethod());

        orderRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
