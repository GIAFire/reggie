package com.wjb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.User;
import com.wjb.reggie.mapper.UserMapper;
import com.wjb.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//用户
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultInfo login(String code, String phone) {
        // 1. 对比验证码（预留位置）

        // 2.根据手机号查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User user = userMapper.selectOne(wrapper);

        // 3.新用户帮他注册
        if (user == null) {
            user = new User();
            user.setPhone(phone); // 手机号
            user.setStatus(1); // 状态
            userMapper.insert(user);
        } else {
            // 4.老用户判断状态是否为禁用
            if (user.getStatus() != 1) {
                throw new CustomException("此用户已被禁用，请联系企业客服~~");
            }
        }

        // 5.登录成功
        return ResultInfo.success(user);
    }
}
