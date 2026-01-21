package com.poly.lab8.DAO;

import com.poly.lab8.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Long> {
    List<Order> findByAccountUsername(String username);
}