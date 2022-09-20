package com.wjb.reggie.controller;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Cart;
import com.wjb.reggie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    // 添加购物车
    @PostMapping("cart/add")
    public ResultInfo cartAdd(@RequestBody Cart cartParam) {
        // 调用service添加
        Cart cart = cartService.cartAdd(cartParam);
        // 返回购物车数据
        return ResultInfo.success(cart);
    }

    // 减少购物车
    @PostMapping("cart/sub")
    public ResultInfo sub(@RequestBody Cart cartParam) {
        // 调用service添加
        Cart cart = cartService.cartSub(cartParam);
        // 返回购物车数据
        return ResultInfo.success(cart);
    }

    // 查询购物车列表
    @GetMapping("/cart/list")
    public ResultInfo cartList() {
        // 调用service查询
        List<Cart> cartList = cartService.cartList();
        // 返回结果
        return ResultInfo.success(cartList);
    }


    // 清空购物车
    @DeleteMapping("/cart/clean")
    public ResultInfo cartClean(){
        // 调用service删除
        cartService.cartClean();
        // 返回成功
        return ResultInfo.success(null);
    }

}
