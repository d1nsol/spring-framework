package com.d1nsol.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// logger interceptor
public class LoggerInterceptor implements HandlerInterceptor{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        logger.info("================ preHandle START =================");
        logger.info("Request URI : ", request.getRequestURI());
        logger.info("Remote Addr : ", request.getRemoteAddr());
        logger.info("Remote Host : ", request.getRemoteHost());
        logger.info("Remote Port : ", request.getRemotePort());
        logger.info("================ preHandle END ===================");

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("================= postHandle START =================");
        logger.info("================= postHandle END ===================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        
    }
}
