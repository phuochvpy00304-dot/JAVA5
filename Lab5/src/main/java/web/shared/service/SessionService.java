package web.shared.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    HttpSession session;  // Spring tiêm sẵn session hiện tại

    // --- Lấy giá trị attribute ---
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) session.getAttribute(name); // lấy attribute ra, có thể là bất kỳ kiểu dữ liệu nào
    }

    // --- Ghi giá trị attribute ---
    public void set(String name, Object value) {
        session.setAttribute(name, value);      // tạo mới hoặc ghi đè nếu tồn tại
    }

    // --- Xóa attribute ---
    public void remove(String name) {
        session.removeAttribute(name);
    }
}
