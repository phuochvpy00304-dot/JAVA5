package web.controller; // hoặc web.config, miễn import đúng khi đặt file

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean("messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:i18n/layout");    // không ghi _vi, _en, không có .properties
        ms.setDefaultEncoding("utf-8");
        return ms;
    }

    @Bean("localeResolver")
    public LocaleResolver localeResolver() {
        CookieLocaleResolver r = new CookieLocaleResolver(); // lưu lựa chọn vào cookie
        r.setCookiePath("/");
        r.setCookieMaxAge(Duration.ofDays(30));
        r.setDefaultLocale(new Locale("vi"));                // mặc định tiếng Việt
        return r;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor i = new LocaleChangeInterceptor();
        i.setParamName("lang");                               // chọn ngôn ngữ bằng ?lang=vi/en
        registry.addInterceptor(i);
    }
}
