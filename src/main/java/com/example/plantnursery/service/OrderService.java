package com.example.plantnursery.service;

import com.example.plantnursery.DTOs.DeliveryRequest;
import com.example.plantnursery.model.*;
import com.example.plantnursery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private AddressRepository addressRepository;

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

    public void updateDeliveryDetails(Long orderId, DeliveryRequest deliveryRequest) throws Exception {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        order.setDeliveryMethod(deliveryRequest.getDeliveryMethod());
        if (deliveryRequest.getDeliveryMethod() == Orders.DeliveryMethod.SHIPPING) {
            Address address;

            if (deliveryRequest.getAddressId() != null) {
                // Fetch the address by ID
                address = addressRepository.findById(deliveryRequest.getAddressId())
                        .orElseThrow(() -> new Exception("Address not found"));
            } else if (deliveryRequest.getAddress() != null) {
                // Use the manually entered address
                address = deliveryRequest.getAddress();
            } else {
                throw new Exception("No address provided for shipping");
            }

            // Set the shipping address in the order
            order.setAddress(address);
        } else if (deliveryRequest.getDeliveryMethod() == Orders.DeliveryMethod.PICKUP) {
            // Handle pickup store selection logic here
            Long storeId = deliveryRequest.getStoreId();
            if (storeId == null) {
                throw new Exception("No store selected for pickup");
            }
            order.setStoreId(storeId);
        }

        orderRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders findById(Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        return order.orElse(null);
    }
}
