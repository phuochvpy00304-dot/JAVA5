package web.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.model.Staff;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @GetMapping("/form")
    public String showForm(Model model, @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "staff-create";
    }

    @PostMapping("/save")
    public String save(Model model,
                       @Valid @ModelAttribute("staff") Staff staff,
                       Errors errors,
                       @RequestPart(value = "photo_file", required = false)
                       MultipartFile photoFile) {

        if (photoFile != null && !photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getOriginalFilename());
        }

        if (errors.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi bên dưới!");
        } else {
            model.addAttribute("message", "Xin chào " + staff.getFullname());
        }


        return "staff-create";                               // → Quay lại form
    }
}

