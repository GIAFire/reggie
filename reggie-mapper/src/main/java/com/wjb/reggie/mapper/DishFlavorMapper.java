package com.wjb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjb.reggie.domain.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}