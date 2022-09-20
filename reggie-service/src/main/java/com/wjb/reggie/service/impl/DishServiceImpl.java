package com.wjb.reggie.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.domain.*;
import com.wjb.reggie.mapper.*;
import com.wjb.reggie.service.DishService;
import com.wjb.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishService dishService;

    @Override
    public Page<Dish> findByPage(Integer pageNum, Integer pageSize, String name) {
        // 1.查询菜品分页数据
        // 1-1 查询条件封装
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(name), Dish::getName, name);
        // 1-2 分页条件封装
        Page<Dish> page = new Page<>(pageNum, pageSize);
        // 1-3 执行mapper查询
        page = dishMapper.selectPage(page, wrapper);
        // 2.遍历每一个菜品对象
        List<Dish> dishList = page.getRecords();
        if (CollectionUtil.isNotEmpty(dishList)) {
            for (Dish dish : dishList) {
                // 3.查询分类对象
                Category category = categoryMapper.selectById(dish.getCategoryId());
                dish.setCategoryName(category.getName());

                // 4.查询口味列表
                // 4-1 封装口味的查询条件
                LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
                dishFlavorWrapper.eq(DishFlavor::getDishId, dish.getId());
                // 4-2 查询list
                List<DishFlavor> dishFlavorList = dishFlavorMapper.selectList(dishFlavorWrapper);
                // 4-3 封装到菜品对象中
                dish.setFlavors(dishFlavorList);
            }
        }

        return page; // 菜品（分类、口味）
    }

    @Override
    public void save(Dish dish) {
        // 1.保存菜品的基本信息
        log.info("菜品保存之前：{}", dish.getId());
        dishMapper.insert(dish);
        log.info("菜品保存之后：{}", dish.getId());

        // 2.保存菜品的口味
        List<DishFlavor> flavorList = dish.getFlavors();
        if (CollectionUtil.isNotEmpty(flavorList)) {
            for (DishFlavor dishFlavor : flavorList) {
                // 指定菜品id
                dishFlavor.setDishId(dish.getId());
                // 保存
                dishFlavorMapper.insert(dishFlavor);
            }
        }
    }

    @Override
    public Dish findById(Long id) {
        // 1.先查菜品基本信息
        Dish dish = dishMapper.selectById(id);
        // 2.在查询口味列表
        // 2-1 构建口味的查询条件对象
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        // 2-2查询列表
        List<DishFlavor> flavorList = dishFlavorMapper.selectList(wrapper);
        // 3.将口味列表设置到菜品对象中
        dish.setFlavors(flavorList);
        // 4.返回菜品对象
        return dish;
    }

    // 菜品修改
    @Override
    public void update(Dish dish) {
        // 1.先更新菜品基本信息
        dishMapper.updateById(dish);

        // 2.删除菜品原有的口味
        // 2-1 构建口味条件对象
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dish.getId());
        // 2-2 执行mapper删除
        dishFlavorMapper.delete(wrapper);

        // 突然发生异常
        // int i = 1 / 0;

        // 3.遍历前端提交的口味
        List<DishFlavor> flavorList = dish.getFlavors();
        if (CollectionUtil.isNotEmpty(flavorList)) {
            for (DishFlavor dishFlavor : flavorList) {
                // 设置菜品id
                dishFlavor.setDishId(dish.getId());
                // 调用mapper保存口味
                dishFlavorMapper.insert(dishFlavor);
            }
        }
    }

    @Override
    public void deleteBatchIds(List<Long> ids) {
        // 删除菜品之前，判断：起售状态不允许删除
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.in(Dish::getId, ids); // id in(?,?)
        dishWrapper.eq(Dish::getStatus, 1); // status = 1
        Integer count = dishMapper.selectCount(dishWrapper);
        if (count > 0) {
            throw new CustomException("删除的菜品必须为停售状态...");
        }

        // 先删菜品
        dishMapper.deleteBatchIds(ids);

        // 再删口味
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(DishFlavor::getDishId, ids);
        dishFlavorMapper.delete(wrapper);
    }

    @Override
    public void updateStatus(Integer status, List<Long> ids) {
        // 条件对象
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getId, ids);
        // 修改实体
        Dish dish = new Dish();
        dish.setStatus(status);
        // 调用mapper
        dishMapper.update(dish, wrapper);
    }

    // 根据分类id查询菜品列表
    @Override
    public List<Dish> findListByCategoryId(Long categoryId,String name) {
        // 1.构建条件
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null){
            wrapper.eq(Dish::getCategoryId, categoryId ); // category_id = xxx
            wrapper.eq(Dish::getStatus, 1); // statuas = 1
        }else if (name != null){
            wrapper.like(Dish::getName, name );
            wrapper.eq(Dish::getStatus, 1);
        }

        // 2.查询list
        return dishMapper.selectList(wrapper);
    }

    // h5菜品展示
    @Override
    public List<Dish> findDishList(Long categoryId, Integer status) {
        // 1.查询菜品列表
        // 1-1 构建条件
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, categoryId);
        dishWrapper.eq(Dish::getStatus,status );
        // 1-2 查询
        List<Dish> dishList = dishMapper.selectList(dishWrapper);

        // 2.遍历菜品列表
        if (CollectionUtil.isNotEmpty(dishList)) {
            for (Dish dish : dishList) {
                // 3.查询菜品对应口味列表
                // 3-1 构建条件
                LambdaQueryWrapper<DishFlavor> flavorWrapper = new LambdaQueryWrapper<>();
                flavorWrapper.eq(DishFlavor::getDishId, dish.getId());
                // 3-2 查询口味
                List<DishFlavor> flavorList = dishFlavorMapper.selectList(flavorWrapper);
                // 3-3 设置菜品中
                dish.setFlavors(flavorList);
            }
        }

        // 4.返回结果
        return dishList;
    }

    @Override
    public List<Dish> dishesInfoById(Long id) {
        List<SetmealDish> setmealDishes = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>()
                .eq(SetmealDish::getSetmealId, id));
        List<Dish> dishes = new ArrayList<>();
        for (SetmealDish setmealDish : setmealDishes){
            Dish byId = dishService.findById(setmealDish.getDishId());
            dishes.add(byId);
        }
        return dishes;
    }
}
