package com.wjb.reggie.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Category;
import com.wjb.reggie.mapper.CategoryMapper;
import com.wjb.reggie.service.CategoryService;
import com.wjb.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    // 新增分类
    @Override
    public void save(Category category) {
        // 1.补齐参数
        // 1-1 id
        long id = IdUtil.getSnowflake(1, 1).nextId();
        category.setId(id);
        // 1-2 创建、更新时间
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        // 1-3 创建、更新人
        category.setCreateUser(1L); // 暂时写死1L
        category.setUpdateUser(1L);// 暂时写死1L

        // 2.调用mapper新增
        categoryMapper.save(category);
    }

    // 修改分类
    @Override
    public void update(Category category) {
        // 1.补齐参数
        category.setUpdateTime(new Date());
        category.setUpdateUser(1l);// 暂时写死

        // 2.调用mapper修改
        categoryMapper.updateById(category);
    }

    // 删除分类
    @Override
    public void delete(Long id) {
        // 1.查询菜品数量
        Integer count1 = categoryMapper.countDishByCategoryId(id);
        if (count1 > 0) {
            // return ResultInfo.error("该分类下还有菜品不能删除");
            throw new CustomException("该分类下还有菜品不能删除");
        }
        // 2.查询套餐数量
        Integer count2 = categoryMapper.countSetmealByCategoryId(id);
        if (count2 > 0) {
            //  return ResultInfo.error("该分类下还有套餐不能删除");
            throw new CustomException("该分类下还有套餐不能删除");
        }
        // 3.删除分类
        categoryMapper.delete(id);
    }

    // 根据type查询分类列表
    @Override
    public List<Category> findByType(Integer type) {
        // 1.构建查询条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type );
        // 2.调用mapper查询
        return categoryMapper.selectList(wrapper);
    }
}
