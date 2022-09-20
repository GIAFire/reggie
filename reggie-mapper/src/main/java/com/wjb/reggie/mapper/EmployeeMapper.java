package com.wjb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjb.reggie.domain.Employee;

import java.util.List;

public interface EmployeeMapper extends BaseMapper<Employee> {

    // 根据用户名查询
    Employee findByUsername(String username);

    // 根据姓名查询
    List<Employee> findByName(String name);

    // 员工新增
    void save(Employee employee);
    // 根据id查询
    Employee findById(Long id);

    // 修改员工
    void update(Employee employee);
}
