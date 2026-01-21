package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class OkController {

    @GetMapping("/ok")
    public String ok() {
        return "ok";
    }

    @PostMapping("/ctrl/ok")
    public String m1(Model model) {
        model.addAttribute("method", "m1() - POST /ctrl/ok");
        return "ok";
    }


    @GetMapping("/ctrl/ok")
    public String m2(Model model) {
        model.addAttribute("method", "m2() - GET /ctrl/ok");
        return "ok";
    }

    @PostMapping(value = "/ctrl/ok", params = "x")
    public String m3(Model model) {
        model.addAttribute("method", "m3() - POST /ctrl/ok?x");
        return "ok";
    }
}
