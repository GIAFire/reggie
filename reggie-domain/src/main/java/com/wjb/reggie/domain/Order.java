package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//订单
@Data
@TableName("orders")
public class Order implements Serializable {

    private Long id;//主键

    private String number; //订单号

    private Integer status;//订单状态 1待付款，2待派送，3已派送，4已完成，5已取消

    private Long userId;//下单用户id

    private Long addressId;//地址id

    private Date orderTime;//下单时间

    private Date checkoutTime;//结账时间

    private Integer payMethod;//支付方式 1微信，2支付宝

    private BigDecimal amount;//实收金额

    private String remark; //备注

    private String userName;//用户名

    private String phone;//手机号

    private String address;//地址

    private String consignee;//收货人

    @TableField(exist = false)
    private String payUrl;//支付地址
}