package com.wjb.reggie.service;

import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Category;

import java.util.List;

public interface CategoryService {

    // 分类列表
    List<Category> findAll();

    // 新增分类
    void save(Category category);

    // 修改分类
    void update(Category category);

    // 删除分类
    void delete(Long id);

    // 根据type查询分类列表
    List<Category> findByType(Integer type);
}
