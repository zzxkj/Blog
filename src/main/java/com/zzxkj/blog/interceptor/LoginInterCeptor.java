package com.zzxkj.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterCeptor extends HandlerInterceptorAdapter {
    /**
     * 预处理链接
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user")==null){
            System.out.println(">>>>>>>>>>session为毛被清空了<<<<<<<<<<<<");
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
