package edu.poly.lab1.controller;

import jakarta.servlet.http.HttpServletRequest; // để lấy tham số từ form
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rectangle")
public class RectController {


    @GetMapping("/form")
    public String form() {
        return "rectangle";
    }

    @PostMapping("/calc")
    public String calc(HttpServletRequest request, Model model) {
        // Lấy giá trị từ form (chuỗi)
        String w = request.getParameter("width");
        String h = request.getParameter("height");

        double width = Double.parseDouble(w);
        double height = Double.parseDouble(h);

        double area = width * height;
        double perimeter = 2 * (width + height);

        // Đưa dữ liệu sang view
        model.addAttribute("width", width);
        model.addAttribute("height", height);
        model.addAttribute("area", area);
        model.addAttribute("perimeter", perimeter);

        return "rectangle";
    }
}
