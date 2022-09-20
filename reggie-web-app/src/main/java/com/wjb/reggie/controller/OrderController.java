package com.wjb.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Order;
import com.wjb.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 用户下单
    @PostMapping("/order/submit")
    public ResultInfo orderSubmit(@RequestBody Order order) {
        // 调用service生成订单
        orderService.save(order);
        // 返回成功
        return ResultInfo.success(null);
    }

    // 用户下单
    @GetMapping("/order/userPage")
    public ResultInfo orderPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Order> row = orderService.orderPage(page,pageSize);
        return ResultInfo.success(row);
    }
}
