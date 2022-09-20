package com.wjb.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import com.wjb.reggie.common.Constant;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.common.UserHolder;
import com.wjb.reggie.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录拦截器
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、判断登录状态，如果已登录，则直接放行
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER);
        if (user != null) {
            //将user方法ThreadLocal
            UserHolder.set(user);
            return true;
        }

        //2、如果未登录,禁止通行,返回错误
        String json = JSON.toJSONString(ResultInfo.error("NOTLOGIN"));
        response.getWriter().write(json);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}