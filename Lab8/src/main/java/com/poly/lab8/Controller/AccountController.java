package com.poly.lab8.Controller;

import com.poly.lab8.Entity.Account;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    HttpSession session;

    @GetMapping("/account/edit-profile")
    public String editProfile(Model model) {
        Account user = (Account) session.getAttribute("user");
        if (user == null) return "redirect:/auth/login";
        model.addAttribute("user", user);
        return "account/edit-profile";
    }

    @GetMapping("/account/change-password")
    public String changePassword() {
        return "account/change-password";
    }
}

