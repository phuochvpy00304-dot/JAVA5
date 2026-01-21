package com.poly.lab8.Service;

import com.poly.lab8.DAO.OrderDAO;
import com.poly.lab8.Entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO dao;

    @Override
    public List<Order> findByUser(String username) {
        return dao.findByAccountUsername(username);
    }
}
