package com.wjb.reggie.controller;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Category;
import com.wjb.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 分类列表
    @GetMapping("/category/findAll")
    public ResultInfo findAll() {
        // 1.调用service查询
        List<Category> list = categoryService.findAll();
        // 2.返回resultInfo结果
        return ResultInfo.success(list);
    }

    // 新增分类
    @PostMapping("/category")
    public ResultInfo save(@RequestBody Category category) { //  1.接收参数

        // 2.调用service新增
        categoryService.save(category);

        // 3.返回resultInfo结果
        return ResultInfo.success(null);
    }

    // 修改分类
    @PutMapping("/category")
    public ResultInfo update(@RequestBody Category category) { // 1.接收参数
        // 2.调用serivce修改
        categoryService.update(category);

        // 3.返回resultInfo结果
        return ResultInfo.success(null);
    }

    // 删除分类
    @DeleteMapping("/category")
    public ResultInfo delete(Long id) { // 1.接收参数
        // 2.调用service删除
        categoryService.delete(id);
        // 3.返回删除结果
        return ResultInfo.success(null);
    }

    // 根据type查询分类列表
    @GetMapping("/category/list")
    public ResultInfo categoryList(Integer type) { // 1.接收请求参数
        // 2.调用service查询
        List<Category> list = categoryService.findByType(type);
        // 3.返回结果
        return ResultInfo.success(list);

    }
}
