package com.wjb.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.domain.Dish;
import com.wjb.reggie.domain.Order;

public interface OrderService {

    void save(Order order);

    Page<Order> findByPage(Integer pageNum, Integer pageSize, String number, String beginTime, String endTime);

    Page<Order> orderPage(Integer page, Integer pageSize);
}
