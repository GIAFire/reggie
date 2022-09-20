package com.wjb.reggie.service;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Employee;

import java.util.List;

public interface EmployeeService {

    // 员工登录
    ResultInfo login(String username, String password);

    // 员工列表
    List<Employee> findList(String name);

    // 新增员工
    void save(Employee employee);

    // 根据id查询
    Employee findById(Long id);

    // 修改员工
    void update(Employee employee);
}
