package com.wjb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjb.reggie.domain.Dish;
import org.springframework.stereotype.Repository;

@Repository // 仅仅是为了解决service依赖报错问题（idea）
public interface DishMapper extends BaseMapper<Dish> {
}
