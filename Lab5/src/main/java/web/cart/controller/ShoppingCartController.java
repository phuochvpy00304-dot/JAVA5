package web.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.cart.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

    @Autowired
    ShoppingCartService service;

    // Hiển thị giỏ hàng
    @RequestMapping("/cart/view")
    public String view(Model model) {
        model.addAttribute("cart", service);
        return "cart/index";
    }

    // Thêm vào giỏ
    @RequestMapping("/cart/add/{id}")
    public String add(@PathVariable Integer id) {
        service.add(id);
        return "redirect:/cart/view";
    }

    // Xóa 1 sản phẩm
    @RequestMapping("/cart/remove/{id}")
    public String remove(@PathVariable Integer id) {
        service.remove(id);
        return "redirect:/cart/view";
    }

    @RequestMapping("/cart/update/{id}")
    public String update(@PathVariable Integer id, @RequestParam Integer qty) {
        service.update(id, qty);
        return "redirect:/cart/view";
    }

    // Xóa hết giỏ
    @RequestMapping("/cart/clear")
    public String clear() {
        service.clear();
        return "redirect:/cart/view";
    }
}
