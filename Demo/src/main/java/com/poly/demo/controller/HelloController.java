package com.poly.demo.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()
public class HelloController {
    @RequestMapping("/poly/hello")
    public String hello(Model model){
        model.addAttribute("title", "Hello World");
        model.addAttribute("subject", "Spring Boot MVC _ Le Nguyen Hoang Trung");
        return "hello";
    }

}
