package com.wjb.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

// 员工
@Data
public class Employee implements Serializable {

    //状态标识常量
    public static final Integer STATUS_DISABLE = 0;
    public static final Integer STATUS_ENABLE = 1;

    private Long id;//主键

    private String name;//姓名

    private String username;//用户名


    private String password;//密码

    private String phone;//手机号

    private String sex;//性别

    private String idNumber;//身份证号

    private Integer status;//状态 0:禁用 1:正常

    /*
        @TableField
            fill = FieldFill.自动填充
                    DEFAULT,  不开启自动填充工程
                    INSERT,   在新增时触发自动填充
                    UPDATE,   在修改时触发自动填充
                    INSERT_UPDATE;  在新增或修改时触发

    */
   // @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//创建时间
    // @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;//更新时间
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;//创建用户
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;//更新用户
}