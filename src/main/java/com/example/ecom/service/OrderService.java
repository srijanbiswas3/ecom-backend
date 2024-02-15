package com.example.ecom.service;

import java.util.List;

import com.example.ecom.entity.Order;

public interface OrderService {

    public List<Order> getUserOrders(String accessToken);

}
