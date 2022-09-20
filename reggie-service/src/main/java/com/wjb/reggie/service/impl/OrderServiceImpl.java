package com.wjb.reggie.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.common.UserHolder;
import com.wjb.reggie.domain.*;
import com.wjb.reggie.mapper.AddressMapper;
import com.wjb.reggie.mapper.OrderDetailMapper;
import com.wjb.reggie.mapper.OrderMapper;
import com.wjb.reggie.service.CartService;
import com.wjb.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public void save(Order order) {
// 1.获取用户信息
        User user = UserHolder.get();
        // 2.获取购物车信息
        List<Cart> cartList = cartService.cartList();
        if (CollectionUtil.isEmpty(cartList)) {
            throw new CustomException("购物车为空不能下单");
        }
        // 3.获取地址信息
        Address address = addressMapper.selectById(order.getAddressId());
        if (address == null) {
            throw new CustomException("收货地址为空不能下单");
        }
        // 4.封装订单明细并保存
        // 4-1 使用雪花算法生成订单id
        long orderId = IdWorker.getId();// mp提供的工具类
        // 4-2 订单总金额
        BigDecimal amount = new BigDecimal(0);
        // 4-3 遍历购物车封装订单明细
        for (Cart cart : cartList) {
            // 创建订单明细
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(cart.getName()); // 购物车菜品名称
            orderDetail.setOrderId(orderId);// 订单id
            orderDetail.setDishId(cart.getDishId());// 购物车菜品id
            orderDetail.setSetmealId(cart.getSetmealId());// 购物车套餐id
            orderDetail.setDishFlavor(cart.getDishFlavor());// 购物车口味
            orderDetail.setNumber(cart.getNumber());// 购物车数量
            orderDetail.setAmount(cart.getAmount());// 购物车金额
            orderDetail.setImage(cart.getImage());// 购物车图片

            // 订单总金额累加： amount = amount + (cart.getAmount() * cart.getNumber())
            amount = amount.add(cart.getAmount().multiply(new BigDecimal(cart.getNumber())));
            // 保存订单明细
            orderDetailMapper.insert(orderDetail);
        }
        // 5.封装订单并保存
        order.setId(orderId); // 订单id
        order.setNumber(String.valueOf(orderId));// 订单号
        order.setStatus(1); // 1待付款
        order.setUserId(user.getId());// 用户id
        order.setOrderTime(new Date());// 下单时间
        order.setCheckoutTime(new Date()); // 结账时间
        order.setAmount(amount); // 订单金额
        order.setUserName(user.getName()); // 用户名称
        order.setPhone(address.getPhone()); // 收货人手机号
        order.setAddress(address.getDetail()); // 收货人地址
        order.setConsignee(address.getConsignee()); // 收货人名称
        // 保存订单
        orderMapper.insert(order);
        // 6.清空购物车
        cartService.cartClean();
    }

    @Override
    public Page<Order> findByPage(Integer pageNum, Integer pageSize, String number, String beginTime, String endTime) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        orderMapper.selectPage(page,new LambdaQueryWrapper<Order>()
                .eq(StrUtil.isNotEmpty(number),Order::getNumber,number)
                .gt(StrUtil.isNotEmpty(beginTime),Order::getOrderTime,beginTime)
                .lt(StrUtil.isNotEmpty(endTime),Order::getOrderTime,endTime));
        return page;
    }

    @Override
    public Page<Order> orderPage(Integer page, Integer pageSize) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        orderMapper.selectPage(orderPage,new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus,4).orderByDesc(Order::getOrderTime));
        return orderPage;
    }
}
