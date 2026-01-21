package web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDAO;
import web.entity.User;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserDAO userDAO;

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // hiển thị form đăng nhập
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          @SessionAttribute(value="backUrl", required=false) String backUrl,
                          Model model) {

        Optional<User> uo = userDAO.findById(username);
        if (uo.isEmpty() || !uo.get().getPassword().equals(password)) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "login";
        }

        User u = uo.get();
        session.setAttribute("user", u.getId());

        String target = (backUrl != null && !backUrl.isBlank()) ? backUrl : "/users";
        session.removeAttribute("backUrl");
        return "redirect:" + target;
    }

    @ModelAttribute("currentUser")
    public String currentUser(HttpSession ss) {
        Object u = ss.getAttribute("user");
        return u == null ? null : u.toString();
    }
}
