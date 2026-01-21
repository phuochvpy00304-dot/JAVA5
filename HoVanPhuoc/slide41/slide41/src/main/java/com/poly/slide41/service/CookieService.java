package com.poly.slide41.service;

import org.springframework.boot.web.server.Cookie;

public interface CookieService {
    Cookie Create (String name, String value, int expiry);

}
