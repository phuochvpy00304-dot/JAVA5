package com.poly.lab8.Interceptor;

import com.poly.lab8.Entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Account user = (Account) request.getSession().getAttribute("user");
        System.out.println(request.getRequestURI() + ", " + new Date() + ", " + user.getFullname());
        return true;
    }
}