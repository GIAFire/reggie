package com.wjb.reggie.controller;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Dish;
import com.wjb.reggie.domain.Setmeal;
import com.wjb.reggie.service.DishService;
import com.wjb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;

    // h5页面套餐展示
    @GetMapping("/setmeal/list")
    public ResultInfo setmealList(Long categoryId, Integer status) { // 1.接收参数

        // 2.调用service
        List<Setmeal> setmealList = setmealService.setmealList(categoryId, status);

        // 3.返回结果
        return ResultInfo.success(setmealList);

    }

    @GetMapping("/setmeal/dish/{id}")
    public ResultInfo findById(@PathVariable Long id) { // 1.接收参数
        List<Dish> dishList = dishService.dishesInfoById(id);
        return ResultInfo.success(dishList);

    }
}
