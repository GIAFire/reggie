package com.wjb.reggie.service;

import com.wjb.reggie.domain.Cart;

import java.util.List;

public interface CartService {

    // 添加购物车
    Cart cartAdd(Cart cartParam);

    // 查询购物车列表
    List<Cart> cartList();

    // 清空购物车
    void cartClean();

    Cart cartSub(Cart cartParam);
}
