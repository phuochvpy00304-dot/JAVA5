package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ParamController {

    @RequestMapping("/param/form")
    public String form() {
        return "param/form";
    }


    @PostMapping("/param/save/{x}")

    public String save(@PathVariable("x") String x,   // lấy x từ đường dẫn {x}
                       @RequestParam("y") String y,   // lấy y từ form input name="y"
                       Model model) {

        model.addAttribute("x", x);
        model.addAttribute("y", y);
        return "param/form";
    }


}
