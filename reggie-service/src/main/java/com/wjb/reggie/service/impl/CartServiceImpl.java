package com.wjb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjb.reggie.common.UserHolder;
import com.wjb.reggie.domain.Cart;
import com.wjb.reggie.mapper.CartMapper;
import com.wjb.reggie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public Cart cartAdd(Cart cartParam) {
        // 1.先查询是否有此购物项 条件：用户id+菜品id 、 用户id+套餐id
        // 1-1 构建条件
        LambdaQueryWrapper<Cart> cartWrapper = new LambdaQueryWrapper<>();
        cartWrapper.eq(Cart::getUserId, UserHolder.get().getId()); // 用户id
        cartWrapper.eq(cartParam.getDishId() != null, Cart::getDishId, cartParam.getDishId()); // 菜品id
        cartWrapper.eq(cartParam.getSetmealId() != null, Cart::getSetmealId, cartParam.getSetmealId()); // 套餐id
        // 1-2 查询记录
        Cart cart = cartMapper.selectOne(cartWrapper);

        // 2.判断
        if (cart == null) {//  新增记录
            cartParam.setNumber(1); // 补齐数量
            cartParam.setUserId(UserHolder.get().getId()); // 补齐用户id
            cartParam.setCreateTime(new Date()); // 添加购物车时间
            cartMapper.insert(cartParam);
            // 3.返回新增购物车
            return cartParam;

        } else { //  数量+1，更新济洛路
            cart.setNumber(cart.getNumber() + 1);
            cartMapper.updateById(cart);
            // 4.返回更新购物车
            return cart;
        }

    }

    // 查询购物车列表
    @Override
    public List<Cart> cartList() {
        // 1.构建条件
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, UserHolder.get().getId());  // 根据当前登录人id

        // 2.查询并返回
        return cartMapper.selectList(wrapper);
    }

    // 清空购物车
    @Override
    public void cartClean() {
        // 1.构建条件
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId,UserHolder.get().getId() );

        // 2.删除购物车
        cartMapper.delete(wrapper);
    }

    @Override
    public Cart cartSub(Cart cartParam) {
        LambdaQueryWrapper<Cart> cartWrapper = new LambdaQueryWrapper<>();
        cartWrapper.eq(Cart::getUserId, UserHolder.get().getId()); // 用户id
        cartWrapper.eq(cartParam.getDishId() != null, Cart::getDishId, cartParam.getDishId()); // 菜品id
        cartWrapper.eq(cartParam.getSetmealId() != null, Cart::getSetmealId, cartParam.getSetmealId()); // 套餐id
        // 1-2 查询记录
        Cart cart = cartMapper.selectOne(cartWrapper);

        // 2.判断
        if (cart.getNumber() <= 1) {//  新增记录
            cartMapper.deleteById(cart.getId());
            // 3.返回新增购物车
            return cartParam;

        } else { //  数量+1，更新济洛路
            cart.setNumber(cart.getNumber() - 1);
            cartMapper.updateById(cart);
            // 4.返回更新购物车
            return cart;
        }
    }
}
