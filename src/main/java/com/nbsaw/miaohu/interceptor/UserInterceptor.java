package com.nbsaw.miaohu.interceptor;

import com.nbsaw.miaohu.common.JwtUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class UserInterceptor implements WebRequestInterceptor {
    private static final JwtUtils JWT_UTILS = new JwtUtils(604800000,"miaohu233333");

    @Override
    public void preHandle(WebRequest request) throws Exception {
        String token =  request.getHeader("token");
        JWT_UTILS.valid(token);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
