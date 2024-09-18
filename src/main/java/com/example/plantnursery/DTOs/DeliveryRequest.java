package com.example.plantnursery.DTOs;

import com.example.plantnursery.model.Address;
import com.example.plantnursery.model.Orders;

public class DeliveryRequest {

    private Orders.DeliveryMethod deliveryMethod;
    private Long storeId;  // For pickup
    private Address address;  // For shipping

    public Orders.DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Orders.DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

