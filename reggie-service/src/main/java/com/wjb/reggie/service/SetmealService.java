package com.wjb.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.domain.Setmeal;

import java.util.List;

//套餐
public interface SetmealService {

    // 套餐分页查询
    Page<Setmeal> findByPage(Integer pageNum, Integer pageSize, String name);

    // 套餐新增
    void save(Setmeal setmeal);

    // 套餐删除
    void deleteBatchIds(List<Long> ids);

    //根据查询套餐信息
    Setmeal findById(Long id);

    //更新套餐信息
    void update(Setmeal setmeal);

    // h5页面套餐展示
    List<Setmeal> setmealList(Long categoryId, Integer status);

}
