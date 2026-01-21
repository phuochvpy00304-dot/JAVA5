package com.poly.lab8.Service;

import com.poly.lab8.Entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByUser(String username);

}
