package web.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {

        // Không tạo mới session: chỉ lấy nếu có
        HttpSession ss = req.getSession(false);
        Object current = (ss != null) ? ss.getAttribute("user") : null;

        // Nếu chưa đăng nhập
        if (current == null) {
            // (Optional) Lưu URL đang định vào để login xong quay lại
            String back = req.getRequestURI()
                    + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
            req.getSession(true).setAttribute("backUrl", back);

            res.sendRedirect("/login");
            return false;
        }

        return true;                        // Có user trong session → cho qua
    }
}
