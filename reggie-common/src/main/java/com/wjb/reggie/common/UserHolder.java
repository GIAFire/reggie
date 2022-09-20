package com.wjb.reggie.common;

import com.wjb.reggie.domain.User;

//ThreadLocal操作工具类
public class UserHolder {

    //维护ThreadLocal对象
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    //放入User
    public static void set(User user) {
        threadLocal.set(user);
    }

    //获取User
    public static User get() {
        return threadLocal.get();
    }

    //移除User
    public static void remove() {
        threadLocal.remove();
    }
}