package edu.poly.lab1.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class AuthController {

    @GetMapping("/form")
    public String form() {
        return "login";
    }

    @PostMapping("/check")
    public String check(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean ok = "poly".equals(username) && "123".equals(password);
        String message = ok ? "Đăng nhập thành công" : "Sai username hoặc password!!!!!";

        model.addAttribute("message", message);
        model.addAttribute("username", username);
        return "login";
    }
}
