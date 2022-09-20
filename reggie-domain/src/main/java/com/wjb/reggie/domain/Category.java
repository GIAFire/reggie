package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//分类
@Data
public class Category implements Serializable {

    private Long id;//主键

    private Integer type;//类型 1 菜品分类 2 套餐分类

    private String name;//分类名称

    private Integer sort; //顺序

    // @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//创建时间

    // @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;//更新时间

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;//创建人

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;//修改人

}