package web.shared.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    public Cookie get(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }


    public String getValue(String name) {
        Cookie c = get(name);
        return (c != null) ? c.getValue() : "";
    }

    // --- Tạo cookie và gửi về client ---
    public Cookie add(String name, String value, int hours) {
        Cookie cookie = new Cookie(name, value);  // tạo cookie mới
        cookie.setPath("/");                      // áp dụng cho toàn site
        cookie.setMaxAge(hours * 3600);           // tuổi thọ tính bằng giây
        response.addCookie(cookie);               // gửi về client
        return cookie;
    }

    // --- Xóa cookie ---
    public void remove(String name) {
        Cookie cookie = new Cookie(name, "");     // tạo cookie trùng tên
        cookie.setPath("/");                      // phải cùng path mới xóa được
        cookie.setMaxAge(0);                      // tuổi thọ = 0 => bị xóa
        response.addCookie(cookie);               // gửi về client
    }
}
