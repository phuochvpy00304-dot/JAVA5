package web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.entity.Flight;
import web.service.FlightService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    private final FlightService service;

    // ================== LIST + SEARCH ==================
    @GetMapping("/")
    public String index(
            @RequestParam(value = "q", required = false) String q,
            Model model) {

        List<Flight> items = service.searchByAirline(q);

        model.addAttribute("items", items);
        model.addAttribute("item", new Flight());
        model.addAttribute("q", q == null ? "" : q);

        return "flight/index";
    }

    // ================== CREATE ==================
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("item") Flight item,
            BindingResult br,
            Model model,
            @RequestParam(value = "q", required = false) String q) {

        if (br.hasErrors()) {
            model.addAttribute("items", service.searchByAirline(q));
            model.addAttribute("q", q == null ? "" : q);
            return "flight/index";
        }

        service.create(item);
        return "redirect:/flights";
    }

    // ================== EDIT ==================
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            @RequestParam(value = "q", required = false) String q,
            Model model) {

        var itemOpt = service.findById(id);
        if (itemOpt.isEmpty()) {
            return "redirect:/flights";
        }

        model.addAttribute("item", itemOpt.get());
        model.addAttribute("items", service.searchByAirline(q));
        model.addAttribute("q", q == null ? "" : q);

        return "flight/index";
    }

    // ================== UPDATE ==================
    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("item") Flight item,
            BindingResult br,
            Model model,
            @RequestParam(value = "q", required = false) String q) {

        if (br.hasErrors()) {
            model.addAttribute("items", service.searchByAirline(q));
            model.addAttribute("q", q == null ? "" : q);
            return "flight/index";
        }

        service.update(item);
        return "redirect:/flights?q=" + (q == null ? "" : q);
    }

    // ================== DELETE ==================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/flights";
    }
}
