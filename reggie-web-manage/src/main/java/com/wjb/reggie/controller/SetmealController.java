package com.wjb.reggie.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Setmeal;
import com.wjb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    // 套餐分页查询
    @GetMapping("/setmeal/page")
    public ResultInfo findByPage(
            String name,
            @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {// 1.接收参数
        // 2.调用service查询
        Page<Setmeal> page = setmealService.findByPage(pageNum, pageSize, name);

        // 3.返回结果
        return ResultInfo.success(page);
    }

    // 套餐新增
    @PostMapping("/setmeal")
    public ResultInfo save(@RequestBody Setmeal setmeal) { // 1.接收参数
        // 2.调用service
        setmealService.save(setmeal);

        // 3.返回结果
        return ResultInfo.success(null);
    }

    // 套餐删除
    @DeleteMapping("/setmeal")
    public ResultInfo deleteBatchIds(@RequestParam List<Long> ids) { // 1.接收参数
        // 2.调用serivce删除
        if (CollectionUtil.isNotEmpty(ids)) {
            setmealService.deleteBatchIds(ids);
        }

        // 3.返回结果
        return ResultInfo.success(null);
    }
}
