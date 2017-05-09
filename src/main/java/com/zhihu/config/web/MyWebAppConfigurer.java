package com.zhihu.config.web;

import com.zhihu.util.BaseUtil;
import com.zhihu.vo.UserInfoVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by Nbsaw on 17-5-5.
 */
@Configuration
public class MyWebAppConfigurer  extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                HttpSession session = request.getSession();
                if (session.getAttribute("data") == null){
                    response.setHeader("Content-Type","application/json;charset=UTF-8");
                    PrintWriter pw = new PrintWriter(response.getOutputStream());
                    String result = BaseUtil.formatResult(401,"请登录后再操作");
                    pw.print(result);
                    pw.flush();
                    pw.close();
                    return false;
                }
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        }).excludePathPatterns("/","/user/login","/oauth/**","/captcha/**","/register/**");
        super.addInterceptors(registry);
    }
}
