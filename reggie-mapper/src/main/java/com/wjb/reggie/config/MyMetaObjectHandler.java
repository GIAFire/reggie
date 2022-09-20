package com.wjb.reggie.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wjb.reggie.common.EmployeeHolder;
import com.wjb.reggie.common.UserHolder;
import com.wjb.reggie.domain.Employee;
import com.wjb.reggie.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

//自定义元数据对象处理器
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入操作，自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());
        Employee employee = EmployeeHolder.get();
        if (employee != null) {
            metaObject.setValue("createUser", employee.getId());
            metaObject.setValue("updateUser", employee.getId());
        }

        User user = UserHolder.get();
        if (user != null) {
            metaObject.setValue("createUser", user.getId());
            metaObject.setValue("updateUser", user.getId());
        }
    }

    //更新操作，自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", new Date());
        Employee employee = EmployeeHolder.get();
        if (employee != null) {
            metaObject.setValue("updateUser", employee.getId());
        }
        User user = UserHolder.get();
        if (user != null) {
            metaObject.setValue("updateUser", user.getId());
        }
    }
}