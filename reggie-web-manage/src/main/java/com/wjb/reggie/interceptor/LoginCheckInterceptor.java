package com.wjb.reggie.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjb.reggie.common.Constant;
import com.wjb.reggie.common.EmployeeHolder;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    // 登录拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取session中员工信息
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute(Constant.SESSION_EMPLOYEE);

        // 2.判断 session中没员工，拦截
        if (employee == null) {
            // 手动封装提示信息
            ResultInfo resultInfo = ResultInfo.error("NOTLOGIN");
            // 手动转json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            // 通过response响应
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return false;
        }

        // 3.session有员工，放行
        EmployeeHolder.set(employee); // 将员工信息存储到线程内map集合
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 服务器响应前，删除线程内map集合
        EmployeeHolder.remove();
    }
}
