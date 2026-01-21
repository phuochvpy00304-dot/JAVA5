package com.poly.lab8.Controller;

import com.poly.lab8.Entity.Account;
import com.poly.lab8.Service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @GetMapping("/auth/login")
    public String loginForm() {

        return "auth/login";
    }

    @PostMapping("/auth/login")
    public String loginProcess(Model model,
                               @RequestParam String username,
                               @RequestParam String password) {
        Account user = accountService.findById(username);
        if (user == null) {
            model.addAttribute("message", "Invalid email!");
        } else if (!user.getPassword().equals(password)) {
            model.addAttribute("message", "Invalid password!");
        } else {
            session.setAttribute("user", user);
            model.addAttribute("message", "Login successfully!");
            String securityUri = (String) session.getAttribute("securityUri");
            if (securityUri != null) return "redirect:" + securityUri;
        }
        return "auth/login";
    }
}