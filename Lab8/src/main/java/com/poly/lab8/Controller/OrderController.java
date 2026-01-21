package com.poly.lab8.Controller;

import com.poly.lab8.Entity.Account;
import com.poly.lab8.Entity.Order;
import com.poly.lab8.Service.OrderService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService; // bạn cần tạo OrderService và OrderDAO

    @Autowired
    HttpSession session;

    @GetMapping("/list")
    public String listOrders(Model model) {
        Account user = (Account) session.getAttribute("user");
        List<Order> orders = orderService.findByUser(user.getUsername());
        model.addAttribute("orders", orders);
        return "order/list"; // cần tạo file order/list.html
    }
}