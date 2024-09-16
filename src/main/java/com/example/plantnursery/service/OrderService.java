package com.example.plantnursery.service;

import com.example.plantnursery.DTOs.OrderRequest;
import com.example.plantnursery.DTOs.OrderItemRequest;
import com.example.plantnursery.model.Customer;
import com.example.plantnursery.model.Item;
import com.example.plantnursery.model.OrderItem;
import com.example.plantnursery.model.Orders;
import com.example.plantnursery.repository.CustomerRepository;
import com.example.plantnursery.repository.ItemRepository;
import com.example.plantnursery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Orders createOrder(Long customerId, List<OrderItemRequest> items, Orders.DeliveryMethod deliveryMethod) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<OrderItem> orderItems = items.stream().map(itemRequest -> {

            Item item = itemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(itemRequest.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());

        double totalAmount = calculateTotalAmount(orderItems);


        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setDeliveryMethod(deliveryMethod);
        orders.setOrderItems(orderItems);
        orders.setTotalAmount(totalAmount);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrders(orders);
        }

        return orderRepository.save(orders);
    }

    private double calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getItem().getPrice() * orderItem.getQuantity())
                .sum();
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Orders> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
