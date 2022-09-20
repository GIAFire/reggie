package com.wjb.reggie.controller;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Category;
import com.wjb.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //根据type查询
    @GetMapping("/category/list")
    public ResultInfo findList() {
        List<Category> categoryList = categoryService.findAll();
        return ResultInfo.success(categoryList);
    }
}