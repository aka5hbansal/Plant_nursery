package com.example.plantnursery.DTOs;

import com.example.plantnursery.model.Orders;

import java.util.List;

public class OrderRequest {
    private Long customerId;
    private List<OrderItemRequest> items;
    private Orders.DeliveryMethod deliveryMethod;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public Orders.DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Orders.DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
}



