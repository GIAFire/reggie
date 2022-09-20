package com.wjb.reggie.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

//订单明细
@Data
public class OrderDetail implements Serializable {

    private Long id;//主键

    private String name;//名称

    private Long orderId;//订单id

    private Long dishId;//菜品id

    private Long setmealId;//套餐id

    private String dishFlavor;//口味

    private Integer number;//数量

    private BigDecimal amount;//金额

    private String image;//图片
}