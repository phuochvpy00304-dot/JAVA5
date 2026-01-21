package web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.entity.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
@Service
public class UserController {

        @Autowired
    UserService service;

    @GetMapping
    public String index (@RequestParam (value = "q", required = false)
                             String q, Model model){
        List<User> items = service.search(q);
        model.addAttribute("items",items);
        model.addAttribute("item", new User());
        model.addAttribute("q", q == null ? "" : q);
        return  "user/index";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("item") User item,
                         BindingResult br, Model model){
        if (br.hasErrors()){
            model.addAttribute("items", service.findAll());
            return "user/index";
        }
        service.save(item);
        return "redirect:/users";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("item") User item,
                         BindingResult br, Model model){
        if (br.hasErrors()){
            model.addAttribute("items", service.findAll());
            return "user/index";
        }
        service.update(item);
        return "redirect:/users";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable String id,
                       @RequestParam(value = "q", required = false) String q,
                       Model model) {
        model.addAttribute("item", service.findById(id).orElse(new User())); // Nếu null -> form rỗng
        model.addAttribute("items", service.search(q));
        model.addAttribute("q", q == null ? "" : q);
        return  "user/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        service.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/reset")
    public String reset(){
        return "redirect:/users";
    }
}
