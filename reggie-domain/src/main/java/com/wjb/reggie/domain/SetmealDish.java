package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//套餐菜品关系
@Data
public class SetmealDish implements Serializable {

    private Long id;//主键

    private Long setmealId;//套餐id

    private Long dishId;//菜品id

    private String name;//菜品名称 （冗余字段）

    private BigDecimal price;//菜品原价

    private Integer copies;//份数

    private Integer sort;//排序

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    // 逻辑删除
    @TableLogic(value = "0",delval = "1") // 正常状态：0、 删除状态：1
    private  Integer isDeleted;
}