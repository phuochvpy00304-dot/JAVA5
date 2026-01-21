package web.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.Staff;


@Controller
@RequestMapping("/staff")
public class StaffController {

    private List<Staff> seed() {
        return List.of(
                Staff.builder().id("user1@gmail.com").fullname("Nguyễn Văn User1").level(0).build(),
                Staff.builder().id("user2@gmail.com").fullname("Nguyễn Văn User2").level(1).build(),
                Staff.builder().id("user3@gmail.com").fullname("Nguyễn Văn User3").level(2).build(),
                Staff.builder().id("user4@gmail.com").fullname("Nguyễn Văn User4").level(2).build(),
                Staff.builder().id("user5@gmail.com").fullname("Nguyễn Văn User5").level(1).build(),
                Staff.builder().id("user6@gmail.com").fullname("Nguyễn Văn User6").level(0).build()
        );
    }

    //         BÀI 1: CHI TIẾT 1 STAFF
    @RequestMapping("/detail")
    public String detail(Model model) {
        Staff staff = Staff.builder()
                .id("username@gmai;.com")
                .fullname("Nguyễn Văn tèo")
                .level(2)
                .build();
        model.addAttribute("staff", staff);
        return "demo/StaffDetail";
    }

    //         BÀI 2: LIST STAFF
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", seed());
        return "demo/StaffList";
    }

    //      BÀI 4: LIST STATUS
    @RequestMapping("/status")
    public String status(Model model) {
        model.addAttribute("list", seed());
        return "demo/StaffStatus";
    }

    // BÀI 5 FILL SELECT BOX
    @RequestMapping("/control")
    public String control(Model model) {
        model.addAttribute("list", seed());
        return "demo/StaffControl";
    }

    @RequestMapping("/them")
    public String them(Model model) {
        model.addAttribute("list", seed());
        return "demo/them";
    }
}
