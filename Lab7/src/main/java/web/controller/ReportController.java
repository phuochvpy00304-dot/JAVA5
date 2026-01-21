package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dao.ProductDAO;
import web.dao.Report;


import java.util.List;

@Controller
public class ReportController {

    @Autowired
    ProductDAO dao;

    @RequestMapping("/report/inventory-by-category")
    public String inventory(Model model) {
        model.addAttribute("items", dao.getInventoryByCategory());                
        return "report/inventory-by-category";
    }
}
