package com.wjb.reggie.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.wjb.reggie.common.Constant;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Employee;
import com.wjb.reggie.mapper.EmployeeMapper;
import com.wjb.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResultInfo login(String username, String password) {
        // 1.根据username调用mapper查询
        Employee employee = employeeMapper.findByUsername(username);
        if (employee == null) {
            return ResultInfo.error("员工不存在");
        }
        // 2.比对密码
        // 2-1 将前端密码进行加密
        String md5Pwd = SecureUtil.md5(password);
        // 2-2 取出数据密码，比对
        if (!StrUtil.equals(employee.getPassword(), md5Pwd)) { // 解决空指针问题
            return ResultInfo.error("密码不正确");
        }
        // 3.员工是否禁用
        if (employee.getStatus() == 0) {
            return ResultInfo.error("此员工被冻结，请联系管理员");
        }
        // 4.登录成功，返回结果
        return ResultInfo.success(employee);
    }

    @Override
    public List<Employee> findList(String name) {
        // 1.调用mapper查询
        List<Employee> list = employeeMapper.findByName(name);
        // 2.返回结果
        return list;
    }

    @Override
    public void save(Employee employee) {
        // 1.补齐参数
        // 1-1 id  雪花算法ID生成器（课下作业）
        Long id = IdUtil.getSnowflake(1, 1).nextId();
        employee.setId(id);
        // 1-2 密码 md5加密
        String md5Pwd = SecureUtil.md5(Constant.INIT_PASSWORD);
        employee.setPassword(md5Pwd);
        // 1-3 员工状态
        employee.setStatus(Employee.STATUS_ENABLE);
        // 创建、更新时间
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        // 创建、更新人
        employee.setCreateUser(1L);
        employee.setUpdateUser(1L);

        // 2.调用mapper新增
        employeeMapper.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        // 直接调用mapper
        return employeeMapper.findById(id);
    }

    @Override
    public void update(Employee employee) {
        // 1.补齐参数
        employee.setUpdateTime(new Date());
        employee.setUpdateUser(1L); // 暂时写死1L

        // 2.调用mapper修改
        employeeMapper.updateById(employee);
    }
}
