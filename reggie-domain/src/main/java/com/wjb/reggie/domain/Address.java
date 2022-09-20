package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


//地址簿
@Data
@TableName("address_book")
public class Address implements Serializable {
    private Long id;//主键

    private Long userId;//用户id

    private String consignee;//收货人

    private String phone; //手机号

    private String sex;//性别 0 女 1 男

    private String provinceCode;//省级区划编号

    private String provinceName;//省级名称

    private String cityCode;//市级区划编号

    private String cityName;//市级名称

    private String districtCode;//区级区划编号

    private String districtName;//区级名称

    private String detail;//详细地址

    private String label;//标签

    private Integer isDefault;//是否默认 0 否 1是

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

    //是否删除
    private Integer isDeleted;
}