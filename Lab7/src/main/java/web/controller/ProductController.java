package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.dao.ProductDAO;
import web.entity.Product;
import web.service.SessionService;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    SessionService session;

    @Autowired
    private ProductDAO dao;

    @RequestMapping("/product/search")
    public String search(Model model,
                         @RequestParam("min") Optional<Double> min,
                         @RequestParam("max") Optional<Double> max,
                         @RequestParam("keywords") Optional<String> kw) {

        double minPrice = min.orElse(Double.MIN_VALUE);
        double maxPrice = max.orElse(Double.MAX_VALUE);
        String keywords = kw.orElse("");

        List<Product> items = dao.findByNameLikeAndPriceBetween("%" + keywords + "%", minPrice, maxPrice);
        model.addAttribute("items", items);
        model.addAttribute("keywords", keywords);
        return "product/search";
    }

    // Bài 2
    @RequestMapping("/product/search-and-page")
    public String searchAndPage(Model model,
                                @RequestParam("keywords") Optional<String> kw,
                                @RequestParam("p") Optional<Integer> p) {

        String keywords = kw.orElse(session.get("keywords", ""));
        session.set("keywords", keywords);

        Pageable pageable = PageRequest.of(p.orElse(0), 5);

        Page<Product> page = dao.findAllByNameLike("%" + keywords + "%", pageable);

        model.addAttribute("keywords", keywords);
        model.addAttribute("page", page);

        return "product/search-and-page";
    }
}
