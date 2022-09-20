package com.wjb.reggie.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.domain.Category;
import com.wjb.reggie.domain.Dish;
import com.wjb.reggie.domain.Setmeal;
import com.wjb.reggie.domain.SetmealDish;
import com.wjb.reggie.mapper.CategoryMapper;
import com.wjb.reggie.mapper.SetmealDishMapper;
import com.wjb.reggie.mapper.SetmealMapper;
import com.wjb.reggie.service.DishService;
import com.wjb.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//套餐
@Service
@Transactional
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishService dishService;

    @Override
    public Page<Setmeal> findByPage(Integer pageNum, Integer pageSize, String name) {
        // 1.先查套餐基本信息
        // 1-1 构建条件对象
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.like(StrUtil.isNotEmpty(name), Setmeal::getName, name);
        // 1-2 构建分页对象
        Page<Setmeal> page = new Page<>(pageNum, pageSize);
        // 1-3 查询
        page = setmealMapper.selectPage(page, setmealWrapper);

        // 2.获取套餐list集合并遍历
        List<Setmeal> setmealList = page.getRecords();
        if (CollectionUtil.isNotEmpty(setmealList)) {
            for (Setmeal setmeal : setmealList) {
                // 3.根据category_id查询分类对象
                Category category = categoryMapper.selectById(setmeal.getCategoryId());
                // 将分类名称关联到套餐中
                setmeal.setCategoryName(category.getName());
                // 4.根据setmeal的id查询菜品（中间表）列表
                // 4-1 构建中间表条件对象
                LambdaQueryWrapper<SetmealDish> sdWrapper = new LambdaQueryWrapper<>();
                sdWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
                // 4-2  查询套菜菜品集合
                List<SetmealDish> setmealDishList = setmealDishMapper.selectList(sdWrapper);
                // 4-3 关联到套餐中
                setmeal.setSetmealDishes(setmealDishList);
            }
        }
        // 5.返回结果
        return page;
    }

    // 套餐新增
    @Override
    public void save(Setmeal setmeal) {
        // 1.先保存套餐基本信息
        setmealMapper.insert(setmeal);
        log.info("保存套餐基本信息，id：{},名称：{},价格：{}", setmeal.getId(), setmeal.getName(), setmeal.getPrice());

        // 2.取出套餐菜品列表
        List<SetmealDish> dishList = setmeal.getSetmealDishes();
        if (CollectionUtil.isNotEmpty(dishList)) {
            for (SetmealDish setmealDish : dishList) {
                // 关联套餐id
                setmealDish.setSetmealId(setmeal.getId());
                // 保存套餐菜品
                setmealDishMapper.insert(setmealDish);
            }
        }
    }

    @Override
    public void deleteBatchIds(List<Long> ids) {
        // 1.先判断套餐状态
        // 1-1 构建套餐条件对象
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.in(Setmeal::getId, ids);
        setmealWrapper.eq(Setmeal::getStatus, 1);
        // 1-2 查询套餐数量
        Integer count = setmealMapper.selectCount(setmealWrapper);
        if (count > 0) {
            throw new CustomException("删除的套餐状态必须为停售~~~");
        }

        // 2.再删除套餐
        setmealMapper.deleteBatchIds(ids);

        // 3.最后删除套餐菜品
        // 3-1 构建套餐菜品条件对象
        LambdaQueryWrapper<SetmealDish> sdWrapper = new LambdaQueryWrapper<>();
        sdWrapper.in(SetmealDish::getSetmealId, ids);
        // 3-2 条件删除
        setmealDishMapper.delete(sdWrapper);
    }

    @Override
    public Setmeal findById(Long id) {
        //1. 查询套餐信息
        Setmeal setmeal = setmealMapper.selectById(id);

        //2. 根据setmeal中的category_id 从category表查询name
        Category category = categoryMapper.selectById(setmeal.getCategoryId());
        setmeal.setCategoryName(category.getName());

        //3. 根据setmeal的id从setmeal_dish表中查询列表
        LambdaQueryWrapper<SetmealDish> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> setmealDisheList = setmealDishMapper.selectList(wrapper1);
        setmeal.setSetmealDishes(setmealDisheList);

        return setmeal;
    }

    @Override
    public void update(Setmeal setmeal) {
        //1. 根据主键更新菜品表信息
        setmealMapper.updateById(setmeal);

        //2. 根据套餐id删除中间表中相关菜品
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        setmealDishMapper.delete(wrapper);

        //3. 向中间表保存新的菜品列表
        List<SetmealDish> setmealDishList = setmeal.getSetmealDishes();
        if (CollectionUtil.isNotEmpty(setmealDishList)) {
            for (SetmealDish setmealDish : setmealDishList) {
                setmealDish.setSetmealId(setmeal.getId());
                setmealDishMapper.insert(setmealDish);
            }
        }
    }

    // h5页面套餐展示
    @Override
    public List<Setmeal> setmealList(Long categoryId, Integer status) {
        // 1.构建条件
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Setmeal::getCategoryId, categoryId);
        wrapper.eq(Setmeal::getStatus, status);
        // 2.执行查询
        List<Setmeal> setmealList = setmealMapper.selectList(wrapper);
        // 3.返回结果
        return setmealList;
    }


}
