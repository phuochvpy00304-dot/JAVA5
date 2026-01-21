package web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import web.security.AuthInterceptor;

/**
 * Nơi "nối" Interceptor vào các URL cụ thể.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry reg) {
        // 1) Bắt buộc đăng nhập cho toàn bộ /users/**
        reg.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/users/**")
                // RẤT QUAN TRỌNG: loại trừ những đường dẫn không nên chặn
                .excludePathPatterns("/login", "/logout", "/error/**",
                        "/css/**", "/js/**", "/images/**");

    }
}
