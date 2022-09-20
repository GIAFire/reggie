package com.wjb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjb.reggie.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}