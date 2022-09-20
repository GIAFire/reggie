package com.wjb.reggie.controller;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Dish;
import com.wjb.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    // h5菜品展示
    @GetMapping("/dish/list")
    public ResultInfo dishList(Long categoryId, Integer status) {// 1.接收参数
        // 2.调用service
        List<Dish> dishList = dishService.findDishList(categoryId, status);
        // 3.返回结果
        return ResultInfo.success(dishList);

    }
}
