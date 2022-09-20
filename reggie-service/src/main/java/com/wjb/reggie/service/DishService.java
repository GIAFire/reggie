package com.wjb.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.domain.Dish;
import com.wjb.reggie.domain.Setmeal;

import java.util.List;

public interface DishService {

    // 分页查询
    Page<Dish> findByPage(Integer pageNum, Integer pageSize, String name);

    // 新增菜品
    void save(Dish dish);

    // 菜品回显
    Dish findById(Long id);

    // 菜品修改
    void update(Dish dish);

    // 菜品删除
    void deleteBatchIds(List<Long> ids);

    void updateStatus(Integer status, List<Long> ids);

    // 根据分类id查询菜品列表
    List<Dish> findListByCategoryId(Long categoryId,String name);

    // h5菜品展示
    List<Dish> findDishList(Long categoryId, Integer status);

    List<Dish> dishesInfoById(Long id);
}
