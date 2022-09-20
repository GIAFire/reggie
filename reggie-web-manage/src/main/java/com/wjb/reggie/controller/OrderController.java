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
    @GetMapping("/order/page")
    public ResultInfo orderSubmit(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  String number,
                                  String beginTime,
                                  String endTime) {
        Page<Order> page = orderService.findByPage(pageNum,pageSize,number,beginTime,endTime);
        // 返回成功
        return ResultInfo.success(page);
    }


}
