package com.d1nsol.configuration;


import java.util.Arrays;
import java.util.List;

import com.d1nsol.interceptor.LoggerInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 인터셉터 동작 요청 mapping
    private static final List<String> URL_PATTERNS = Arrays.asList("/");
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns(URL_PATTERNS);
    }
}
