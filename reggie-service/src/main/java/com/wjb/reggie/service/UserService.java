package com.wjb.reggie.service;

import com.wjb.reggie.common.ResultInfo;

//前台用户
public interface UserService {

    // 登录注册
    ResultInfo login(String code, String phone);
}
