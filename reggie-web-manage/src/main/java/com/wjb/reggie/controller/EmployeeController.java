package com.wjb.reggie.controller;

import cn.hutool.core.util.StrUtil;
import com.wjb.reggie.common.Constant;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Employee;
import com.wjb.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

// 内部员工模块
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    HttpSession session;

    @PostMapping("/employee/login")
    public ResultInfo login(@RequestBody Map<String, String> param) {
        // 1.接收请求参数
        String username = param.get("username");
        String password = param.get("password");
        // 2.调用service完成登录
        ResultInfo resultInfo = employeeService.login(username, password);
        // 3.如果登录成功，将员工信息存储session
        if (resultInfo.getCode() == 1) {
            Employee employee = (Employee) resultInfo.getData();
            session.setAttribute(Constant.SESSION_EMPLOYEE, employee);
        }
        // 4.返回结果
        return resultInfo;
    }

    // 退出
    @PostMapping("/employee/logout")
    public ResultInfo logout() {
        // 1.清除session
        session.invalidate();
        // 2.返回结果
        return ResultInfo.success(null);
    }

    // 员工列表
    @GetMapping("/employee/find")
    public ResultInfo findList(String name) { // 1.接收请求参数
        // 2.调用service查询员工列表
        List<Employee> list = employeeService.findList(name);
        // 3.返回结果
        return ResultInfo.success(list);
    }

    // 新增员工
    @PostMapping("/employee")
    public ResultInfo save(@RequestBody Employee employee) {  //  1.接收请求体参数

        // 2.调用service新增
        employeeService.save(employee);

        // 3.返回结果
        return ResultInfo.success(null);
    }

    // 回显员工（根据id查询）
    @GetMapping("/employee/{id}")
    public ResultInfo findById(@PathVariable Long id) { // 1.接收参数
        // 2.调用serivce
        Employee employee = employeeService.findById(id);
        // 3.返回结果
        return ResultInfo.success(employee);

    }

    // 修改员工
    @PutMapping("/employee")
    public ResultInfo update(@RequestBody Employee employee) { // 1.接收参数
        // 2.调用serivce修改
        employeeService.update(employee);
        // 3.返回结果
        return ResultInfo.success(null);
    }
}
