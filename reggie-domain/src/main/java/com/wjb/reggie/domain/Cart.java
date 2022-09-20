package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//购物车
@Data
@TableName("shopping_cart")
public class Cart implements Serializable {

    private Long id;//主键

    private String name;//名称

    private Long userId;//用户id

    private Long dishId;//菜品id

    private Long setmealId;//套餐id

    private String dishFlavor;//口味

    private Integer number;//数量

    private BigDecimal amount;//金额

    private String image;//图片

    private Date createTime;
}