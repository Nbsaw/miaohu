package com.nbsaw.miaohu.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Nbsaw on 17-5-5.
 * 服务器访问拦截器
 */
@Configuration
public class AccessHandler  extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new HandlerInterceptor() {
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                HttpSession session = request.getSession();
//                if (session.getAttribute("id") == null){
//                    response.setHeader("Content-Type","application/json;charset=UTF-8");
//                    PrintWriter pw = new PrintWriter(response.getOutputStream());
//                    String result = JsonUtil.formatResult(401,"not authorized");
//                    pw.print(result);
//                    pw.flush();
//                    pw.close();
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//            }
//
//            @Override
//            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//            }
//        }).excludePathPatterns("/","/user/login","/oauth/**","/captcha/**","/register/**","/asd");
//        super.addInterceptors(registry);
    }
}
