package com.poly.demo.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController2 {
    @Autowired
    ServletContext application;
    @Autowired
    HttpSession session;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @RequestMapping("/url.php")
    public String sayHello(){
        String fullname = request.getParameter("hoTen");
        request.setAttribute("massage", "Http Components - Le Nguyen Hoang Trung");
        return "hello";
    }
}
