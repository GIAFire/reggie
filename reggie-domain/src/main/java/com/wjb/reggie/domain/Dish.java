package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//菜品
@Data
public class Dish implements Serializable {

    private Long id;//主键

    private String name;//菜品名称

    private Long categoryId;//所属分类id

    private BigDecimal price;//菜品价格

    private String code;//商品码

    private String image;//图片

    private String description;//描述信息

    private Integer status;//0 停售 1 起售

    private Integer sort;//顺序

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

    //===================数据表中不存在下面字段,仅仅用于页面显示===================
    //菜品口味列表
    @TableField(exist = false)
    private List<DishFlavor> flavors = new ArrayList<>();

    //菜品分类名称
    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private Integer copies;
}