package com.wjb.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Dish;
import com.wjb.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    // 菜品分页查询
    @GetMapping("/dish/page")
    public ResultInfo findByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            String name) { // 1.接收参数

        // 2.调用service
        Page<Dish> page = dishService.findByPage(pageNum, pageSize, name);

        // 3.返回结果
        return ResultInfo.success(page);

    }

    // 新增菜品
    @PostMapping("/dish")
    public ResultInfo save(@RequestBody Dish dish) { // 1.接收参数
        // 2.调用serivce保存
        dishService.save(dish);
        // 3.返回成功结果
        return ResultInfo.success(null);
    }

    // 菜品回显
    @GetMapping("/dish/{id}") // 前面没有#
    public ResultInfo findById(@PathVariable Long id) { // 1.接收菜品id
        // 2.调用service查询
        Dish dish = dishService.findById(id);
        // 3.返回结果
        return ResultInfo.success(dish);
    }

    // 菜品修改
    @PutMapping("/dish")
    public ResultInfo update(@RequestBody Dish dish) { // 1.接收请求体参数
        // 2.调用service修改
        dishService.update(dish);
        // 3.返回成功消息
        return ResultInfo.success(null);

    }


    // 根据分类id查询菜品列表
    @GetMapping("/dish/list")
    public ResultInfo findList(Long categoryId,String name){
        List<Dish> dishList = dishService.findListByCategoryId(categoryId,name);
        return ResultInfo.success(dishList);
    }
}
