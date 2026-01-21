package com.poly.lab8.Controller;

import com.poly.lab8.Entity.Account;
import com.poly.lab8.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AccountService accountService;

    @GetMapping("/home/index")
    public String home() {
        return "admin/home/index";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        return "admin/users"; // cần tạo file admin/users.html
    }

    @GetMapping("/reports")
    public String viewReports() {
        return "admin/reports"; // cần tạo file admin/reports.html
    }

}

